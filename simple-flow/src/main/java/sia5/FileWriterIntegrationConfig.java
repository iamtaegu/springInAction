package sia5;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.GenericTransformer;

@Configuration
public class FileWriterIntegrationConfig {

  @Profile("xmlconfig")
  @Configuration
  @ImportResource("classpath:/filewriter-config.xml")
  public static class XmlConfiguration {}
  
  @Profile("javaconfig")
  @Bean
  @Transformer(inputChannel="textInChannel", //변환기 빈을 선언
               outputChannel="fileWriterChannel")
  public GenericTransformer<String, String> upperCaseTransformer() {
    return text -> text.toUpperCase();
  }

  @Profile("javaconfig")
  @Bean
  @ServiceActivator(inputChannel="fileWriterChannel")
  //fileWriterChannel로부터 메시지를 받아서
  //FileWritingMessageHandler의 인스턴스로 정의된 서브시에 넘겨줌
  public FileWritingMessageHandler fileWriter() { //파일 쓰기 빈을 선언(파일 쓰기 메시지 핸들러)
    FileWritingMessageHandler handler =
        new FileWritingMessageHandler(new File("/tmp/sia5/files"));
    handler.setExpectReply(false); //응답 채널을 사용하지 않음
    handler.setFileExistsMode(FileExistsMode.APPEND);
    handler.setAppendNewLine(true);
    return handler;
  }

  /**
   * DSL Configuration
   * 위에는 DSL 설정을 사용하지 않은 것임
   * DSL을 통해 빈 메서드 하나에 전체 플로우를 담을 수 있음
   *
   * IntegrationFlows 클래스는 플로우를 선언할 수
   * @return
   */
  @Profile("javadsl")
  @Bean
  public IntegrationFlow fileWriterFlow() {
    return IntegrationFlows
        .from(MessageChannels.direct("textInChannel")) //인바운드 채널
        .<String, String>transform(t -> t.toUpperCase()) //변환기를 선언
        .handle(Files //파일에 쓰는 것을 처리
            .outboundAdapter(new File("/tmp/sia5/files"))
            .fileExistsMode(FileExistsMode.APPEND)
            .appendNewLine(true))
        .get(); 
  }

}
