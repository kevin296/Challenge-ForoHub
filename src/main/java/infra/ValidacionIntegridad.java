package infra;

public class ValidacionIntegridad extends RuntimeException{
    public ValidacionIntegridad(String f){
        super(f);
    }
}
