import frontend.ErrorInfo;
import frontend.Lexer;
import frontend.Parser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static midend.Module.module;

public class Compiler {
    public static void main(String[] args) throws IOException {
        String filePath = "testfile.txt";
        List<String> input = null;
        try {
            input = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Lexer lexer = new Lexer((ArrayList)input);

        ArrayList<ErrorInfo> errors = new ArrayList<>();
        String parserFile = "parser.txt";
        try (FileWriter parserFileWriter = new FileWriter(parserFile, true)) {
            Parser parser = new Parser(lexer.readLine(), parserFileWriter, lexer.getErrors());
            parser.parseCompUnit();
            errors = parser.getErrors();
        } catch (IOException e) {
            e.printStackTrace();
        }

        errors.sort(Comparator.comparingInt(ErrorInfo::getLine));
        String errorFile = "error.txt";
        try (FileWriter writer = new FileWriter(errorFile, true)) {
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

//        String mipsFile = "mips.txt";
//        try (FileWriter mipsFileWriter = new FileWriter(mipsFile, false)) {
//            module.genMips(mipsFileWriter);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
