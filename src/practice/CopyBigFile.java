package practice;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;

public class CopyBigFile {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.printf("Enter source file: ");
        String sourcePath = in.nextLine();
        System.out.printf("Enter destination file: ");
        String destPath = in.nextLine();

        File sourceFile = new File(sourcePath);
        File destFile = new File(destPath);

        try {
            copyFileUsingStream(sourceFile, destFile);
            System.out.printf("copy completed");
        } catch (IOException ioe) {
            System.out.printf("can't copy that file");
            System.out.printf(ioe.getMessage());
        }
    }
    private static void copyFileUsingStream(File source, File dest ) throws IOException {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInputStream.read(buffer)) > 0){
                fileOutputStream.write(buffer, 0, length);
            }
        } finally {
            fileInputStream.close();
            fileOutputStream.close();
        }
    }
}
