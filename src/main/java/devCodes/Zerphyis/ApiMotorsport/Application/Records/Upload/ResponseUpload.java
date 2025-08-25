package devCodes.Zerphyis.ApiMotorsport.Application.Records.Upload;

public record ResponseUpload(   Long id,
                                String nomeArquivo,
                                String url,
                                String conteudo,
                                Long tamanho) {
}
