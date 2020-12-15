
import java.io.File;
public class Blob extends KeyValueObject {

    public Blob(File file) throws Exception {
        this.Type = "blob";
        GenerateKey(file);
    }
    
    //重写toString方法
    @Override
    public String toString() {
        return "100644 blob " + Key;
    }
    

}
