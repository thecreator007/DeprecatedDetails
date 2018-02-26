package All;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Cmd {
    static String[] exten = new String[]{"jar"};
    static final FilenameFilter filter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            for (final String ext : exten) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };
    public static void main(String[] args) throws Exception {

        File dir = new File("C:\\Users\\dje\\Documents\\lib\\");//class path directory
        File[] ff = dir.listFiles(filter);
 String location="C:\\Users\\dje\\Documents\\output.csv";//output file location
        PrintWriter pw=new PrintWriter(location);
        pw.append("TESTED JAR FILES"+",");
        pw.append("ERRORS"+",");
        pw.append("DEPRECATION");


        for (int i = 0; i < ff.length; i++) {
            pw.println();
            ArrayList<String> a=new ArrayList<>();
            ArrayList<String> a1=new ArrayList<>();
            int count=0,count1=0;
            ProcessBuilder b= new ProcessBuilder("jdeprscan","--class-path","*",ff[i].getName());
            b.directory(new File("C:\\Users\\dje\\Documents\\lib\\"));//checking directory

            b.redirectErrorStream(true);
            Process p =  b.start();
            Scanner sc=new Scanner(p.getInputStream());
            while ( sc.hasNextLine()){
                System.out.println(sc.nextLine());
            }
   pw.print(ff[i].getName()+",");
            while ((sc.hasNextLine()))
            {
                String ss=sc.nextLine();
                System.out.println(ss);
                String[] Split = ss.split(" ");
                if(Split[0].equalsIgnoreCase("error:")){
                    count1++;
                }
                if((Split[0].equalsIgnoreCase("class"))||(Split[0].equalsIgnoreCase("interface")))
                {
                    String[] f=Split[5].split("::");
                    String g=f[0].replaceAll("/",".");
                    a.add(g);
                    a1.add(ss);
                    count++;
                }
            }
            if(count1==0&&a1.size()==0){
                pw.print("No errors And No Deprecated Class");
            }else if(count1>0&&a1.size()==0){
                pw.print("No Deprecated Class But Error has to be Resolve");
            }else if(count1>=0&&a1.size()>0){
                int j=1;
                for(String aa:a1){
                    pw.print(" "+j+") "+aa);
                    j++;
                }
            }
            if(count==0){
                pw.print(","+"***No Deprecation***");
            }else {
                int j=1;
                pw.print(",");
                for (String aa : a) {
                    pw.print(" "+j+") "+aa);
                    j++;
                }
            }
        }
        pw.close();
        pw.flush();
    }

}

