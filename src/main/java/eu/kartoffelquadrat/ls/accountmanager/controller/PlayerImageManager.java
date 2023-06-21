package eu.kartoffelquadrat.ls.accountmanager.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PlayerImageManager {

  private final String fileStorePath = "user-images/";


  /**
   * Store a multipart file to the file storage location.
   *
   * @param file
   * @param storeFileName the file name which we want to store the file with. (must end with .png).
   * @throws IOException
   */
  public void storeImage(MultipartFile file, String storeFileName) throws IOException {
    if (!file.isEmpty()) {
      byte[] bytes = file.getBytes();
      Path path = Paths.get(fileStorePath + storeFileName).normalize();
      Files.write(path, bytes);
    }
  }

  /**
   *
   *
   * @param username
   * @throws IOException
   */

  public void deleteImage(String username) throws IOException {
    try (Stream<Path> imagePathsStream = Files.list(Paths.get(fileStorePath))) {
      List<String> fileNames = imagePathsStream
          .filter(Files::isRegularFile)
          .map(Path::getFileName)
          .map(Path::toString)
          .collect(Collectors.toList());
      if (fileNames.stream().anyMatch(fileName -> fileName.contains(username))) {
        // only delete the user image file if there is such a image file
        Path userImagePath = Paths.get(fileStorePath + username + ".png").normalize();
        Files.delete(userImagePath);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
   *
   *
   * @param username
   * @return
   */
  public Resource getImage(String username) {
    try {
      Path imagePath = Paths.get(fileStorePath, username + ".png").normalize();
      return new UrlResource(imagePath.toUri());

    } catch (Exception e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }

  }


}
