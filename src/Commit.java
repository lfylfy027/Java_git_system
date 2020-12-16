import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Commit extends KeyValueObject {
	//parent等信息的写入之后完善
	protected String parent;
	protected String author;
	protected String commiter;
	
	
	
	public Commit(File dir) throws Exception {
		this.File=dir;
		this.Type = "commit";
		this.Value="";
		Value+= "tree "+KeyValue_Storage.storage(dir)+"\n";
		Value+= "parent "+"\n";
		Value+= "author "+"\n";
		Value+= "commiter "+"\n";
		GenerateKey(Value);
	}
	
	@Override
    public void copyFile() throws FileNotFoundException {
    	//输出
		
		//以hash值首字母创建路径
		File dir = new File("objects/"+this.Key.charAt(0));
		if (!dir.exists()) {
			dir.mkdirs();
		}
    	
        PrintWriter p = new PrintWriter("objects/"+this.Key.charAt(0)+"/"+this.Key);
        p.print(Value);
        p.close();
        
        //将commit的key信息放入commits文件夹中，而文件命名为具体项目的名称，查询时通过项目名称找到当前的commit文件
        PrintWriter q = new PrintWriter("commits/"+this.File);
        q.print(this.Key);
        q.close();
    }
}
