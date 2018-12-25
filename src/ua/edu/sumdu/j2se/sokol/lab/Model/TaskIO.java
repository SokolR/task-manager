package ua.edu.sumdu.j2se.sokol.lab.Model;

import java.util.*;
import java.text.*;
import java.io.*;

public class TaskIO {
    private static SimpleDateFormat convert = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss:SSS");


    public static void write(TaskList tasks, OutputStream out) throws IOException {
        if (tasks.size() != 0) {
            DataOutputStream dos = new DataOutputStream(out);

            try {
                dos.writeInt(tasks.size());

                for (Iterator<Task> itr = tasks.iterator(); itr.hasNext();) {
                    Task tmp = itr.next();
                    if (tmp.isRepeated()) {
                        dos.writeUTF(tmp.getTitle());
                        dos.writeBoolean(tmp.isActive());
                        dos.writeLong(tmp.getStartTime().getTime());
                        dos.writeUTF(" ");
                        dos.writeLong(tmp.getEndTime().getTime());
                        dos.writeInt(tmp.getRepeatInterval());
                        dos.writeUTF("\n");
                    }
                    else {
                        dos.writeUTF(tmp.getTitle());
                        dos.writeBoolean( tmp.isActive());
                        dos.writeLong(tmp.getTime().getTime());
                        dos.writeUTF("\n");
                    }
                }

            } finally {
                if (dos != null)
                    dos.close();
            }
        }
        else {System.out.println("Collection is empty!"); }
    }


    public static void read(TaskList tasks, InputStream in) throws IOException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(in));

        String newTitle;
        boolean active;
        int len, repeat;

        try {
            len = dis.readInt();

            for (int i = 0; i < len; i++) {
                newTitle = dis.readUTF();
                active = dis.readBoolean();
                Date start = new Date();
                start.setTime(dis.readLong());
                if (dis.readUTF().equals("\n")){
                    Task buf = new Task(newTitle, start);
                    buf.setActive(active);
                    tasks.add(buf);
                }
                else {
                    Date end = new Date();
                    end.setTime(dis.readLong());
                    repeat = dis.readInt();
                    Task buf = new Task(newTitle, start, end, repeat);
                    buf.setActive(active);
                    tasks.add(buf);
                    dis.readUTF();
                }
            }
        } finally {
            dis.close();
        }
    }


    public static void writeBinary(TaskList tasks, File filename) throws IOException{
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(filename);
            write(tasks, fos);
        } finally {
            fos.close();
        }
    }


    public static void readBinary(TaskList tasks, File filename) throws IOException {
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(filename);
            read(tasks, fis);
        } finally {
            fis.close();
        }
    }


    public static void write(TaskList tasks, Writer out) throws IOException {
        BufferedWriter bw = new BufferedWriter(out);

        try {
            for(Task task : tasks){
                Date start  = task.getStartTime();
                Date end = task.getEndTime();
                Boolean act = task.isActive();
                bw.write(task.getTitle());
                bw.write(";");
                bw.write(convert.format(start));
                bw.write(";");
                bw.write(convert.format(end));
                bw.write(";");
                bw.write(String.valueOf(task.getRepeatInterval()));
                bw.write(";");
                bw.write(String.valueOf(act));
                bw.write(";");
                bw.append("\n");
            }
        }
        finally {
            bw.close();
        }
    }


    public static void read(TaskList tasks, Reader in) throws ParseException {
        Scanner scan = new Scanner(in).useDelimiter(";\\s*");

        try {
            while(scan.hasNext()) {
                String newTitle = scan.next();
                String start = scan.next();
                Date newStart = convert.parse(start);
                String end = scan.next();
                Date newEnd = convert.parse(end);
                String rep = scan.next();
                int newRep = Integer.parseInt(rep);

                if(newRep == 0) {
                    Task task = new Task (newTitle, newStart);
                    task.setActive(Boolean.parseBoolean(scan.next()));
                    tasks.add(task);
                }
                else {
                    Task task = new Task (newTitle, newStart, newEnd, newRep);
                    task.setActive(Boolean.parseBoolean(scan.next()));
                    tasks.add(task);
                }
            }
        }
        finally{
            scan.close();
        }
    }


    public static void writeText(TaskList tasks, File filename)throws IOException {
        FileWriter fw = null;

        try {
            fw = new FileWriter(filename);
            write(tasks, fw);
        } finally {
            fw.close();
        }
    }


    public static void readText(TaskList tasks, File filename)throws IOException, ParseException {
        FileReader fr = null;

        try {
            fr = new FileReader(filename);
            read(tasks, fr);
        } finally {
            fr.close();
        }
    }
}
