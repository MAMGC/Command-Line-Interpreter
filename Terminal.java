package osasgmt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.util.Scanner;

public class Terminal {
   //                         command indx  =  0      1     2    3      4      5      6        7        8       9      10      11      12      13     14     15   16   17
    public static String[] commands =       {"cat", "cd", "ls", "rm" , "cp", "mv", "mkdir", "rmdir", "clear", "more", "pwd", "args", "date", "help", "?" , "Exit",">",">>"};  // not include ";"
    public static int[] num_of_parameters = {  2  ,  1  ,  1  ,  2   ,  2  ,  2  ,   1    ,    1   ,    0   ,    1  ,   0  ,   1   ,    0  ,    0  ,   1 ,   0    }; // el num of paramters eli lazm kol command ya5odha ma3da cd momken ta5od 0,1 & cat 1,2 & ls 0,1 & rm momken ta5od -r and directory aw file bs ... max is stored
    public static int num_of_commands = commands.length;
  
    public static String [] description =   { "Concatenates files and prints on the standard output " , "changes the current directory to another one " , "lists Directory contents sorted alphabetically " , "removes specified file OR delete a directory and recursively delete its content " , "copies the first file onto the second one but it does not copy directories " ,
                                              "moves the given file into a file with the same given name " , "creates a directory with the given name " , "removes an existing empty directory " , "clears the current terminal screen " , "Display and scroll down the output in one direction only you can scroll page by page " , "Display current user directory " , "List all command arguments " , "Display Current date/time " , "List all user commands and the syntax of their arguments " , "Prints the help of the given command " , "Terminates the program" , "Redirct output to be written to the given file (create file if doesn't exist and replace if exists)","Redirct output to be written to the given file (create file if doesn't exist and Append if exists)"      
                                            };
    
    public static String[] syntax = {"cat [File1]  OR  cat [File1]...[File2]"," cd OR cd [Path]"," ls OR ls [Path]","rm [file Path] OR rm -r [Directory path]"," cp [File1 path]...[File2 path]"," mv [File1 path]...[File2 path]","mkdir [Directory path + name]"," rmdir [Directory path + name]","clear","more [File path]","pwd","args [command name]","date","help","? [command name]" ,"Exit","[command name]...[command parameters]...>...[File Path]","[command name]...[command parameters]...>>...[File Path]"};
    public static String default_directory = "D:/";
    File curr_directory = new File(default_directory);

    public int checkCommands(String command) // done .... return the indx of the command according to the commands array else return -1 
    {
        int indx = -1;

        for (int i = 0; i < num_of_commands; i++) {
            if (command.equals(commands[i])) {
                indx = i;
                break;
            }
        }

        if (indx == -1) {
            System.out.println("Error : The Command does not exist"); // just to print error msg to the user
        }

        return indx;

    }

    public int Is_valid(String[] tokens) // check the command and the num of parameters and return indx of command or -1
    {

        boolean correct = false;
        int parameter_indx = checkCommands(tokens[0]);  // parameter indx and command indx are the same

        if (parameter_indx != -1) // y3ny el command mktob sa7
        {
            if ((parameter_indx == 0) || (parameter_indx == 1) || (parameter_indx == 2) || (parameter_indx == 3)) // y3ny command cat OR cd OR ls OR rm 34an dol eli by5tlfo fel max wel min num of parameters
            {
                if (((tokens.length - 1) == num_of_parameters[parameter_indx]) || (((tokens.length - 1) == (num_of_parameters[parameter_indx] - 1)))) // num of parameters lazm = max num of parameters or 2a2l be 1
                {
                    correct = true;
                }
            } else if ((tokens.length - 1) == num_of_parameters[parameter_indx]) // num of parameters == max num of parameters
            {
                correct = true;
            }
        }

        // just to print Error msg to users
        if ((correct == false) && (parameter_indx != -1)) // y3ny el parameters 8alt bs el command sa7
        {
             
            
            if ((parameter_indx == 0) || (parameter_indx == 1) || (parameter_indx == 2)  || (parameter_indx == 3)) {
                System.out.println("Error : This command should take " + (num_of_parameters[parameter_indx] - 1) + " OR " + num_of_parameters[parameter_indx] + " parameters");
            } else {
                System.out.println("Error : This command should take " + num_of_parameters[parameter_indx] + " parameters");
            }
              
            parameter_indx = -1 ; // 34an y3ml handling fel main we myro74 ynfz el command we howa leeh parameters m4 mzbota
        }

        return parameter_indx;

    }

    public void cd(String path) {
        if (path.length() == 0) // empty path ... ha7ot el default directorey
        {
            curr_directory = new File(default_directory);
        } else {

            File f = new File(path);

            if (f.isDirectory()) {
                curr_directory = new File(path);
            } else {
                System.out.println("Error : " + path + " is not a directory ");
            }

        }
    }

    public String ls(String path) {
        
        File folder;
        String res = "";
        
        if (path.length() == 0) // law mfe4 parameter hay2ol el files+folders eli 3ala el path directory eli wa2ef 3leeh
        {
            folder = new File(curr_directory.getPath());
        } else {
            folder = new File(path); // han3rd el files+folders eli 3ala l given path
        }

        if (folder.isDirectory()) // to make sure eno el given path is correct
        {
            File[] Files_List = folder.listFiles(); // list bn4el feha kol el data eli fel folder sorted

            for (int i = 0; i < Files_List.length; i++) 
            {
                if (Files_List[i].isFile()) // file 
                {
                    res += "File : "; 
                    res += Files_List[i].getName();
                    res +="\n";
                    
                }
                else if (Files_List[i].isDirectory()) // folder
                {
                   // System.out.println("Directory " + Files_List[i].getName());
                   res+= "Directory : ";
                   res+=  Files_List[i].getName();
                   res+= "\n";
                }
            }
            
        } 
       
        else 
        {
            System.out.println("Error : " + path + " is not a directory ");
        }
        
         return res ;
 
    }  

    public String pwd()  
    {
        return ("Current Directory is " + curr_directory);
    }

    public String[] split_input(String input) // bt2at3 el input le command we parameters
    {
        String[] tokens = input.split(" ");
        return tokens;

    }

    public void clear() {

        for (int i = 0; i < 10; i++) {
            System.out.println();
        }

    }
  
    FileInputStream fin; // to read content of file 
    BufferedReader br ;
    
    public void open_file(String FileName) {
        try {
            File f = new File (FileName); 
             fin = new FileInputStream (f);
             br = new BufferedReader ( new InputStreamReader (fin));
        } catch (Exception e) {
            System.out.println("Can not find File");
        }
    }

    public boolean Exists(String file_name) // bt4of el file da mwgod 3ala l curr dir wala la2
    {
        File[] Files_List = curr_directory.listFiles();
        boolean found = false;

        for (int i = 0; i < Files_List.length; i++) {
            if (file_name.equals(Files_List[i].getName())) {
                found = true;
                break;
            }
        }

        return found;
    }

    public String read_file(String filename) throws IOException { // used in cat fun 

        String full_path = curr_directory.getPath() + filename; // hadelo el path kaml 34an y3rf y3ml open lel file mn 3ala l curr directroy
        open_file(full_path);
        String data = "";
        
        while(br.ready())
        {           
            data+=br.readLine();
            data+="\n";
        }
        
        return data;
    }

    public String cat(String input) throws IOException {  // read contents of files on the curr directory
            
        String res = "";
            if (Exists(input)) // y3ny law el file mwgod
            {
              res = read_file(input);
            } 
            else
            {
                System.out.println("This File does not exist");
            }
            
       return res ;
    }  // lazm t3ml cat le 2 files 3ala l curr dir
  
    public void cp(String filename1, String filename2) throws IOException  // by3ml copy le 2 files using full path
    {
        File copy_f = new File (filename1);
       
        if (copy_f.exists()) // lazm el file eli ha3mlo copy yekon mawgod
        {
            try 
            {
                BufferedReader inputStream = new BufferedReader(new FileReader(filename1)); // el file eli hay2ra mno 
                File outfile = new File(filename2); // el file eli hayktb 3leeh         
               
                if (!outfile.exists())  // if File doesnt exists, then create it
                {
                    outfile.createNewFile();
                }  
         
                FileWriter filewriter = new FileWriter(outfile.getAbsoluteFile());
                BufferedWriter outputStream = new BufferedWriter(filewriter);
                
                while (inputStream.ready()) //y3ny lsa fe data fel input file
                {
                    outputStream.write(inputStream.readLine()); // hay2ra line we yktbo 3al file 
                    outputStream.newLine(); // ynzl satr gded 
                }
              
                // close all files so we can use them again
                outputStream.flush();  
                outputStream.close();
                inputStream.close();        
               
            } 
            
            catch (IOException e)
            {
                System.out.println(e);
            }
            
        }
        
        else 
        {
            System.out.println("The File you want to copy does not exist");
        }
        
    }
   
    public void deleteDirectory(File directory) 
    {   
      if (directory.exists()) 
      {
        
        File[] files = directory.listFiles(); // list of files eli gowa el folder
        if (files.length != 0) // m3anaha eno feh files mwgoda 
        {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) // law kan folder hay3ml recursion
                {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
     }
      
   directory.delete(); // 34an y3ml delte lel directory b3d ma yfdy mno el files eli gowah
   System.out.println("Directory is deleted");

}
   
    public void rm ( String FilePath , int operation ) // using full path  
    {
        File f1 = new File (FilePath);
       
        if (f1.isDirectory()) // law mb3otlo folder... handling ( rm -r directory ) case  
        {  
            
            deleteDirectory(f1);
            
        }
        
        else if (f1.delete()) 
        {   
            if (operation==1) // just 34an nzbt el cout law kan removed wala moved
            System.out.println("This file is deleted");
            
            else if (operation==2)
              System.out.println("The file moved successfully"); 
            
        } 
        
        else 
        {
            System.out.println("This file is not exist");
        }
        
    }

    public void rmdir(String directoryName) {

        File Directory = new File(directoryName);
        if (Directory.exists()) // asln mawgod
        {
            if (Directory.list().length == 0) // is empty
            {
                Directory.delete();
                System.out.println(directoryName + " is Deleted ");
               
            } else {

                System.out.println("ERROR : Directory is not Empty");
                
            }
        }
        else 
        {
            System.out.println("ERROR : Directory Doesn't Exist ");
        }
        
    }
    
    public void mkdir(String directoryName) {
        File Directory = new File(directoryName);

        if (!Directory.exists()) {
            if (Directory.mkdirs()) {
                System.out.println(directoryName + " is created ");
            }
        } else {
            System.out.println("ERROR Creating Directory ");
        }
    }

    public Date displayDate() 
    {
        Date d = new Date();
        return d;  
    }
  
    public void more (String FileName)        
    {
        File f1=new File (FileName );
        String Buffer=""; 
        try
        {
            Scanner sc = new Scanner(f1);
           
            while (true)   // 34an ykml law l user 3aiz more we lsa fe data fel file
            {
                int counter = 0; // 34an n3d 37 line = print one page
                
                while (sc.hasNextLine() && counter < 37) 
                {
                    counter++;
                    Buffer = sc.nextLine();
                    System.out.println(Buffer);

                }
                
                String user_Enter="" ; // ha5od feh el input bta3 el user if he wants more or no
                
                if (sc.hasNextLine()) // lazm ykon fe data asln mtb2ya fel file 34an a3mlha print abl ma as2l el user 3aiz more or not
                { 
                Scanner in = new Scanner(System.in);
                System.out.println("more...?");
                user_Enter = in.nextLine();
                 
                }
                
                else 
                {   
                    System.out.println("End of File");
                    break;
                }
               
                if (user_Enter.isEmpty()) // m3anaha el user press el Enter key ... y3ny 3aiz more
                {   
                    clear(); // hanms7 we nkml b2et el data
                }
                
                else // m4 3aiz more
                {
                    break ;
                }
                
            }

        }
        
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        
    }
    
    public void mv (String filename1, String filename2) throws IOException
    {
        cp( filename1 , filename2);
        rm( filename1 , 2 ); // lazm el file eli b3mlo move akon w2fa 3al directory bta3o
    }
    
    public void help () // 34an nfr2 been el ? wel help 
    {  
        for (int i=0 ; i<commands.length ; i++)
        {   
            System.out.println( commands[i] + " : " + description[i] + " , Syntax : " + syntax[i]);
            System.out.println("-------------------------------------------------------------------------");
            
        }
    }
    
    public void Q_mark (int command_indx)
    {
            System.out.println( commands[command_indx] + " : " + description[command_indx] + " , Syntax : " + syntax[command_indx]);
            System.out.println("-------------------------------------------------------------------------");       
    }
    
    public void args (int command_indx)
    {
            System.out.println( "Parameters of " + commands[command_indx] + " : " + syntax[command_indx]);
            System.out.println("-------------------------------------------------------------------------");          
    }
    
    public void Redirecting_operator (String[] tokens , boolean append) throws FileNotFoundException, IOException // by4t8l law 2bleh Date , cat , pwd aw ls ... we b3deh esm el file eli hyktb 3leh .. " > " ... if append = true hayb2a ">>"
    {   
        String operator = ">"; // el default
        
        if (append)
        {
            operator = ">>";
        }
        
        int end_of_command = Arrays.asList(tokens).indexOf(operator); // bgeb awl 7aga end of el command eli 2abl el operator 
       
        String [] command_section = Arrays.copyOfRange(tokens, 0, end_of_command); // ba5od copy mn el goz2 eli 2abl el operator 34an a3mlo verify then excution
        String [] File_section = Arrays.copyOfRange(tokens, end_of_command+1 , tokens.length); // el goz2 eli b3d el operator ">" haykon esm el file eli hayktb 3leeh
        
        int command_indx = Is_valid(command_section); // b4of el command mzbot wala la2 .. kmtob sa7
       
        if( (command_indx ==0 ) || (command_indx ==2 ) || (command_indx ==10 ) || (command_indx ==12 )) // y3ny el > da lazm ykon 2bleh commands mo3yna ... cat , ls , pwd , date
        {
             if (File_section.length == 1) // lazm nd5l esm file wa7d bs
             {  
                 String res = ""; // el string eli hatrg3 mn el commands 34an nktbha 3al file
                 
                 if (command_indx == 0) // cat command
                 {
                     for (int j = 1; j < command_section.length; j++) 
                     {
                         res += cat(command_section[j]);
                     }
                     
                 } 
                 
                 else if (command_indx == 2)  // ls command
                 {
                     if (command_section.length==1) // ls
                       res = ls(""); // bnb3to fady 34an ls el curr dir
                     
                     else
                       res = ls(command_section[command_section.length-1]); // hanb3t el path bta3 el dir eli gay b3d ls
                 }
                 
                 else if (command_indx == 10)  // pwd
                 {
                     res = pwd();
                 } 
                 
                 else // date
                 {
                     res += displayDate();
                 }
                 
                 try 
                 {
                     File file = new File(File_section[0]); // bft7 file bel given name
                     
                     if (!file.exists()) // law m4 mwgod bn3mlo create 34an nktb 3leh
                     {
                         file.createNewFile();
                     }
                     
                     FileWriter out = new FileWriter(file , append); // law append == true hayro7 y3ml append fel file else hay3ml replacemet
                     BufferedWriter bw = new BufferedWriter (out);
                     
                     while (res.length()> 0) // y3ny hayfdl yktb line by line to ma el res feha data
                     {   
                         int len = res.indexOf("\n"); 
                        
                         if (len != -1) 
                         {
                             bw.write(res.substring(0,len)); // just to split res to small substrings 34an yktb kol wa7da fe line ... handling fel files bs
                             bw.newLine(); 
                             res = res.substring(len + 1);
                         }
                         
                         else // m3naha eno el res hya gomla w7da mfeha kalam kteer we beno new lines
                         {
                             bw.write(res);
                             bw.newLine();
                             break ;
                         }
                         
                     }
                         
                    bw.flush();
                    bw.close();
                    System.out.println("Done...");
                
                 } 
                 
                 catch (FileNotFoundException fileNotFoundException)
                 {
                     System.err.println("Error");
                 }
                 
                 
             }
             
             else // 34an lazm yktb esm file wa7d bs
             {
                 System.out.println("File name is not valid ");  
             }
            
            
        }
       
        else // el commands el tanya m4 bt4t8l m3 el > aw >>
        {
            System.out.println("This command cannot be executed with the Redirection operator");    
        }
    
 
    }
    
    public static void main(String[] args) throws IOException {

        String input ;
        boolean termination = false ;
        String[] tokens;
        Terminal obj = new Terminal();

        Scanner sc = new Scanner(System.in);
        input = sc.nextLine();
        ArrayList<String> multiple_commands = new ArrayList<String>(); // array bndef feh el inputs to handli ";" case
        
        
        while (!termination) 
        {
            if (input.contains(";"))
            {
                while (true) // bn2sm string le small commads we ndef fel array 34an ttnfz
                {
                    int indx_of = input.indexOf(';');

                    if (indx_of != -1)  
                    {
                        multiple_commands.add(input.substring(0, indx_of)); // kda bndef command .. end of command = ";"

                        if (input.charAt(indx_of + 1) == ' ') // 34an law kona katben command tany lazm a5do mn b3d el space 34an yt3mlo compare sa7 fel check
                        {
                            input = input.substring(indx_of + 2); // ignore space we 5od mn b3dha
                        } 
                        
                        else // law mfe4 space we command el tany mktob b3d el ; 3ala tool
                        {
                            input = input.substring(indx_of + 1);
                        }

                    }
                    
                    else // y3ny 5alas da a5r command we mfe4 7aga tany warah ... mfe4 ";" tany
                    {
                        multiple_commands.add(input.substring(0)); // 7ot a5r command we break
                        break;
                    }

                }
                
            }
            
            else 
            {   
               
                multiple_commands.add(input); // m3naha el user da5l one command per line
            }

            
            for (int i = 0; i < multiple_commands.size(); i++)  // hanm4y 3al array 34an nnfz command command
            {
                
                tokens = obj.split_input(multiple_commands.get(i)); // hena bn2sm el command nfso according lel spaces 34an nnfzo
                
               
                // law kan el command bta3y mawgod feeh el > aw >> hab3to lel fun eli fo2 34an tnfzo m3 el commands el 5asa beeh we a3ml skip le b2et el code 
                if (Arrays.asList(tokens).contains(">"))
                {
                    obj.Redirecting_operator(tokens , false);
                    continue;
                }
                 
                else  if (Arrays.asList(tokens).contains(">>"))
                {
                    obj.Redirecting_operator(tokens , true);
                    continue;
                }
                
               
                int command_indx = obj.Is_valid(tokens);
                
                if (command_indx != -1)   // m3naha l command da mktob s7 wel num of parameters s7
                {

                    String path = "";

                    if (tokens.length > 1) // m3naha eno ba3t command m3ah parameter or path
                    {
                        path = tokens[tokens.length - 1]; // a5r klma li hya el path
                    }

                    if (command_indx == 0) // call cat fun .... lazm el file yekon fel curr directory ... m4 bel full path
                    {
                        for (int j = 1; j < tokens.length; j++) 
                        {
                            System.out.println(obj.cat(tokens[j]));
                        }

                    } 
                    else if (command_indx == 1) // call cd fun
                    {
                        obj.cd(path);

                    } 
                    else if (command_indx == 2) // call ls fun
                    {
                        System.out.println(obj.ls(path));
                    } 
                    else if (command_indx == 3) // call rm fun ... lazm nd5lo l full path
                    {
                        if (tokens.length == 2) // remove file
                        {
                            obj.rm(tokens[1], 1);
                        }
                        
                        else if (tokens[1].equals("-r")) // remove directory
                        {
                            obj.rm(tokens[2], 1);
                        }
                    } 
                    else if (command_indx == 4) // call cp fun
                    {
                        obj.cp(tokens[1], tokens[2]);

                    } 
                    else if (command_indx == 5) // call mv fum .... bta5od l full path brdo 
                    {
                        obj.mv(tokens[1], tokens[2]);
                    } 
                    else if (command_indx == 6) // call mkdir fun .... must write the path 
                    {
                        obj.mkdir(tokens[1]);

                    } 
                    else if (command_indx == 7) // call rmdir fun .... must write the full path 
                    {
                        obj.rmdir(tokens[1]);

                    } 
                    else if (command_indx == 8) // call clear fun
                    {
                        obj.clear();

                    } 
                    else if (command_indx == 9) // call more fun .... bta5od el full path bta3 el file
                    {
                        obj.more(tokens[1]);

                    } 
                    else if (command_indx == 10) // call pwd fun
                    {
                        System.out.println(obj.pwd());

                    } 
                    else if (command_indx == 11) // call args fun
                    {
                        int indx = obj.checkCommands(tokens[1]); // bn4of el command eli md5lo b3d args s7 asln 

                        if (indx != -1) 
                        {
                            obj.args(indx);
                        }
                         
                    } 
                    else if (command_indx == 12) // call date fun
                    {
                        System.out.println(obj.displayDate());

                    } 
                    else if (command_indx == 13) // call help fun
                    {
                        obj.help();

                    } 
                    else if (command_indx == 14) // call ? fun
                    {
                        int indx = obj.checkCommands(tokens[1]); // bn4of el command eli md5lo b3d ? s7 asln 
                        //System.out.println("indx="+indx);
                        
                        if (indx != -1) {
                            obj.Q_mark(indx);
                        }

                    } 
                    else if (command_indx == 15) // Exit fun
                    {
                        System.out.println("Program Terminates");
                        termination = true ; // 34an may5d4 input tany we y5rog bara el loop
                        break;
                    }

                }
           
            }
            
            if (termination == false)
            {
                input = sc.nextLine();
                multiple_commands.clear(); // 34an maynfz4 el command eli et3mlt before tany bfdy el array 34an ytmli bel new commands
            }
        
        }
    
    }

}
