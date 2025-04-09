import frontend.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static midend.Module.module;

public class Compiler {
    public static void main(String[] args) throws IOException {
//        try {
//            String command = "java -jar src/errorProcess.jar";
//
//            Process process = Runtime.getRuntime().exec(command);
//
//            int exitCode = process.waitFor();
//            System.out.println("Jar 执行完毕，退出码: " + exitCode);
//
//            File file = new File("error.txt");
//
//            if (file.length() != 0) {
//                System.out.println("编译错误");
//                return;
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }

        String filePath = "testfile.txt";
        List<String> input = null;
        try {
            input = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Lexer lexer = new Lexer((ArrayList)input);

        ArrayList<Token> tokens = lexer.readLine();

        ArrayList<ErrorInfo> errors = new ArrayList<>();
        String parserFile = "parser.txt";
        try (FileWriter parserFileWriter = new FileWriter(parserFile, false)) {
            ErrorParser errorParser = new ErrorParser(tokens, parserFileWriter, lexer.getErrors());
            errorParser.parseCompUnit();
            errors = errorParser.getErrors();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(errors.size() != 0) {
            errors.sort(Comparator.comparingInt(ErrorInfo::getLine));
            String errorFile = "error.txt";
            String lastErrorString = null; // 用于保存上一个错误的字符串表示
            try (FileWriter writer = new FileWriter(errorFile, false)) {
                for (ErrorInfo error : errors) {
                    String currentErrorString = error.toString();
                    if (!currentErrorString.equals(lastErrorString)) {
                        writer.write(currentErrorString + "\n");
                        lastErrorString = currentErrorString; // 更新 lastErrorString
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        errors.clear();
        try (FileWriter parserFileWriter = new FileWriter(parserFile, false)) {
            Parser parser = new Parser(tokens, parserFileWriter, lexer.getErrors());
            parser.parseCompUnit();
            errors = parser.getErrors();
        } catch (IOException e) {
            e.printStackTrace();
        }

        errors.sort(Comparator.comparingInt(ErrorInfo::getLine));
        String errorFile = "error.txt";
        try (FileWriter writer = new FileWriter(errorFile, false)) {
            for (ErrorInfo error : errors) {
                writer.write(error.toString() + "\n");
            }
        }catch (IOException e) {
                e.printStackTrace();
        }

        String llvmFile = "llvm_ir.txt";
        try (FileWriter llvmFileWriter = new FileWriter(llvmFile, false)) {
            llvmFileWriter.write(module.print());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // System.out.println(module.print());
    }
}
