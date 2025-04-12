public class AggregateStats implements Directive {
    private String byteColumn;
    private String timeColumn;
    private String outputSizeCol;
    private String outputTimeCol;
    private long totalBytes = 0;
    private long totalTimeMillis = 0;

    @Override
    public UsageDefinition define() {
        return UsageDefinition.builder("aggregate-stats")
            .withRequiredArg("byteColumn")
            .withRequiredArg("timeColumn")
            .withRequiredArg("outputSizeCol")
            .withRequiredArg("outputTimeCol")
            .build();
    }

    @Override
    public void initialize(Arguments arguments) {
        byteColumn = arguments.value("byteColumn");
        timeColumn = arguments.value("timeColumn");
        outputSizeCol = arguments.value("outputSizeCol");
        outputTimeCol = arguments.value("outputTimeCol");
    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext ctx) {
        for (Row row : rows) {
            Object byteVal = row.getValue(byteColumn);
            Object timeVal = row.getValue(timeColumn);

            long bytes = new ByteSize(byteVal.toString()).getBytes();
            long millis = new TimeDuration(timeVal.toString()).getMillis();

            totalBytes += bytes;
            totalTimeMillis += millis;
        }

        Row result = new Row();
        result.add(outputSizeCol, (double)totalBytes / (1024 * 1024)); // MB
        result.add(outputTimeCol, (double)totalTimeMillis / 1000); // seconds
        return Collections.singletonList(result);
    }
}
