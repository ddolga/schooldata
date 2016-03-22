package api;


public interface FormProcessInterface {

    void process(String inFileName, String outFileName, CellProcessorInterface cellProcessor, int limit);

}
