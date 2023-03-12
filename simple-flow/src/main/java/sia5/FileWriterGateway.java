package sia5;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * 애플리케이션에서 통합 플로우로 데이터를 전송하는 게이트웨이
 */
@MessagingGateway(defaultRequestChannel="textInChannel") //메시지 게이트웨이를 선언
public interface FileWriterGateway {

  void writeToFile(
      @Header(FileHeaders.FILENAME) String filename, //파일에 씀
      String data);
  
}
