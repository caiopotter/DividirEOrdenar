import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

	private static void comporNovoArquivoCep(RandomAccessFile novoArquivo1, RandomAccessFile novoArquivo2, RandomAccessFile novoArquivoCepCompleto, byte buffer[]) throws IOException {
		OutputStream saidaFinal = new FileOutputStream("C:\\Downloads\\arquivoFinal.dat");
		DataOutputStream doutFinal = new DataOutputStream(saidaFinal);
		
		Endereco endereco1 = new Endereco();
		Endereco endereco2 = new Endereco();
		System.out.println("Lendo endereços: ");
		novoArquivo1.seek(0L);
		novoArquivo2.seek(0L);
		endereco1.leEndereco(novoArquivo1);
		endereco2.leEndereco(novoArquivo2);
		
		while(novoArquivo1.getFilePointer() <= novoArquivo1.length() || novoArquivo2.getFilePointer() <= novoArquivo2.length()) {
			
			if(endereco1.getCep().compareTo(endereco2.getCep()) > 0) {
				endereco2.escreveEndereco(doutFinal);
				if(novoArquivo2.getFilePointer() == novoArquivo2.length()) {
					escreveRestanteArquivo(novoArquivo1, endereco1, doutFinal);
					break;
				}
				endereco2.leEndereco(novoArquivo2);
				
			} else {
				endereco1.escreveEndereco(doutFinal);
				if(novoArquivo1.getFilePointer() == novoArquivo1.length()) {
					escreveRestanteArquivo(novoArquivo2, endereco2, doutFinal);
					break;
				}
				endereco1.leEndereco(novoArquivo1);
				
			}
		}
		novoArquivo1.close();
		novoArquivo2.close();
		doutFinal.close();
		saidaFinal.close();
		System.out.println("FIM");
	}

	private static void escreveRestanteArquivo(RandomAccessFile novoArquivo, Endereco endereco, DataOutputStream doutFinal) throws IOException {
		while(novoArquivo.getFilePointer() < novoArquivo.length()) {
			endereco.escreveEndereco(doutFinal);
			endereco.leEndereco(novoArquivo);
		}
		endereco.escreveEndereco(doutFinal);
		
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
