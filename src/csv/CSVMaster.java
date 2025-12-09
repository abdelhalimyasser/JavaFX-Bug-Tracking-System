import java.util.*;
import java.io.*;
class CSVMaster {
    private final String filePath;
    private final boolean hasHeader;
    public CSVMaster(String filePath,boolean hasHeader){
        this.filePath=filePath;
        this.hasHeader=hasHeader;
    }
    public List<String[]> readAll() throws IOException{
        List<String[]> rows=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader(filePath))){
            String line;
            boolean first=true;
            while((line=br.readLine())!=null){
                if(first&&hasHeader){
                    first=false;
                    continue;
                }
                if(line.trim().isEmpty()){
                    continue;
                }
                String[]parts=line.split(",");
                rows.add(parts);
            }
        }
        return rows;
        
    }
}
