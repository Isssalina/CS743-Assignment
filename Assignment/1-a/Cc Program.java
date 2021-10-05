
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class CcProgram {

    public static void main(String[] args) throws IOException {
        String function = "";
        Scanner scan = new Scanner(System.in);
        System.out.println("""
                To add new children please enter 0.
                To remove a child please enter 1.
                """);
        if (scan.hasNextLine()) {
            function = scan.nextLine();
        }
        if (Integer.parseInt(function) == 0) {
            Add();
        } else if (Integer.parseInt(function) == 1) {
            Remove();
        } else {
            System.out.println("Invalid input!\n");
        }
        scan.close();
    }

    public static boolean isAlphabeticStr(String str){
        char[] c = str.toCharArray();
        boolean isAlphabetic;
        for (char value : c) {
            isAlphabetic = (value >= 'a' && value <= 'z') || (value >= 'A' && value <= 'Z');
            if (!isAlphabetic) {
                return false;
            }
        }
        return true;
    }

    public static void Add() throws IOException {
        String path = "";
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter file path:\n");
        if (scan.hasNextLine()) {
            path = scan.nextLine();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        String str;
        int counter = 0;
        ArrayList<Integer> idNum = new ArrayList<>();
        while ((str = br.readLine()) !=null){
            counter++;
            String[] s = str.split(",");
            File result = new File("result.txt");
            if(!result.exists()){
                try{
                    result.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(result,true)));
            boolean validInput = true;

            //Determine whether the name is valid
            int length = s[0].length();
            boolean is_boolean = isAlphabeticStr(s[0]);
            if(s[0] == null){
                out.write("Missing Name at line:"+counter);
                validInput = false;
            }
            if((length < 2) || (length > 20) || (!is_boolean)){
                out.write("Invalid Name at line:" + counter);
                validInput = false;
            }

            //Determine whether the ID is valid
            int id = 0;
            try {
                id = Integer.parseInt(s[1]);
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (s[1] == null){
                out.write("Missing Age at line:"+counter);
                validInput = false;
            }
            if((id <100) || (id > 999)){
                out.write("Invalid ID at line:" + counter);
                validInput = false;
            }
            if(validInput){
                id =Integer.parseInt(s[1]);
                idNum.add(id);
                HashSet<Integer> uniqueId=new HashSet<>(idNum);
                if(idNum.size()!=uniqueId.size()){
                    if(!idNum.isEmpty()){
                        idNum.remove(idNum.size() - 1);
                    }
                    out.write("Id number is not unique at line:"+ counter);
                    validInput = false;
                }
            }

            //Determine whether the age is valid
            int age = 0;
            try {
                age = Integer.parseInt(s[2]);
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if(s.length<3){
                out.write("Missing age at line:"+counter);
                validInput = false;
            }
            if ((age <2) || (age > 60)){
                out.write("Invalid Age at line:"+ counter);
                validInput = false;
            }

            //add child to the list
            if(validInput){
                out.write("Child at line"+ counter + " has been added successfully.");
                File list = new File("list.txt");
                if(!list.exists()){
                    try{
                        list.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                BufferedWriter output=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(list,true)));
                for(int i=0; i< s.length; i++){
                    if(i<2){
                        output.write(s[i]+",");
                    }else {
                        output.write(s[i]);
                    }
                }
                output.write("\r\n");
                output.flush();
                output.close();
            }
            out.write("\r\n");
            out.flush();
            out.close();
        }
        br.close();
        scan.close();
    }

    public static void Remove() throws IOException {
        File list = new File("list.txt");
        if(!list.exists()){
            System.out.println("The list file does not exist!");
            return;
        }
        String deleteInfo="";
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the child’s ID to delete the information:\n");
        if (scan.hasNextLine()) {
            deleteInfo = scan.nextLine();
        }
        String rl;
        StringBuilder bf = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(list));
        while(( rl = br.readLine()) != null)
        {
            rl = rl.trim();
            if(!rl.contains(deleteInfo)){ //或者!r1.startsWith(special)
                bf.append(rl).append("\r\n");
            }
        }
        br.close();
        BufferedWriter out = new BufferedWriter(new FileWriter(list));
        out.write(bf.toString());
        out.flush();
        out.close();
        System.out.println("The child has been removed successfully!");
    }


}

