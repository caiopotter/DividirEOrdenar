import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class DividirEOrdenarMain {
	public static void main(String[] args) {
		byte buffer[] = new byte[300];
		RandomAccessFile arquivoCepOriginal = null;
		RandomAccessFile novoArquivo1 = null;
		RandomAccessFile novoArquivo2 = null;
		RandomAccessFile novoArquivoCepCompleto = null;
		
		try {
			arquivoCepOriginal = new RandomAccessFile("C:\\Downloads\\cep_ordenado.dat", "r");
			novoArquivo1 = new RandomAccessFile("C:\\Downloads\\cep_ordenado_dividido_1.dat", "rw");
			novoArquivo2 = new RandomAccessFile("C:\\Downloads\\cep_ordenado_dividido_2.dat", "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			dividirArquivoEmDois(arquivoCepOriginal, novoArquivo1, novoArquivo2, buffer);
			comporNovoArquivoCep(novoArquivo1, novoArquivo2, novoArquivoCepCompleto, buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

	// TODO: TERMINAR ISSO AQUI
	private static void comporNovoArquivoCep(RandomAccessFile novoArquivo1, RandomAccessFile novoArquivo2, RandomAccessFile novoArquivoCepCompleto, byte buffer[]) throws IOException {
		Endereco endereco = new Endereco();
		Long contador1 = 0L;
		Long contador2 = 0L;
		int qtd = 0;
		while(qtd >= 0) {
			qtd = novoArquivo1.read(buffer);
			if(qtd < 0)
				break;
			novoArquivo1.seek(contador1*300L);
			endereco.leEndereco(novoArquivo1);
			//System.out.println(endereco.getCep() + " " + endereco.getLogradouro());
			contador1++;
		}
		System.out.println("FIM");
	}

	private static void dividirArquivoEmDois(RandomAccessFile arquivoCepOriginal, RandomAccessFile novoArquivo1, RandomAccessFile novoArquivo2, byte buffer[]) throws IOException {
		System.out.println("Iniciando Divisão do arquivo de CEP em dois arquivos menores...");
		boolean seletor; 
		int qtd = 0;
		arquivoCepOriginal.seek(0L);
		
		while(qtd >= 0) {
			seletor = gerarBooleanAleatorio();
			qtd = arquivoCepOriginal.read(buffer);
			if(seletor == true) {
				novoArquivo1.write(buffer);
			}else {
				novoArquivo2.write(buffer);
			}
		}
		System.out.println("Divisão concluída.");
	}


	private static boolean gerarBooleanAleatorio() {
		Random random = new Random();
		return random.nextBoolean();
	}


}
