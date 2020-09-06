package names;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

//this class allows a ZipInputStream to create InputStreams for each ZipEntry
public class ZippedFileInputStream extends InputStream {

  private ZipInputStream is;

  public ZippedFileInputStream(ZipInputStream is) {
    this.is = is;
  }

  @Override
  public int read() throws IOException {
    return is.read();
  }

  @Override
  public void close() throws IOException {
    is.closeEntry();
  }
}
