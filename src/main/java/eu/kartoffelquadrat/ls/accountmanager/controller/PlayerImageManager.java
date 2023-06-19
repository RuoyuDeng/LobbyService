package eu.kartoffelquadrat.ls.accountmanager.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PlayerImageManager {

  private final String fileStorePath = "user-images/";


  /**
   * Store a multipart file to the file storage location
   *
   * @param file
   * @param storeFileName the file name which we want to store the file with. (must end with .png).
   * @throws IOException
   */
  public void storeImage(MultipartFile file, String storeFileName) throws IOException {
    if (!file.isEmpty()) {
      byte[] bytes = file.getBytes();
      Path path = Paths.get(fileStorePath + storeFileName);
      Files.write(path, bytes);
    }
  }

  public void deleteImage(String username) {

  }

  public void getImage(String username) {

  }


}
