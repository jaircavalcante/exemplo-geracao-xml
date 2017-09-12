import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class GeraXML {
	
	private static File file = new File("C:\\Temp\\Contatos.xml");

    private static void salvarArquivo(String documento) throws Exception {
        FileOutputStream fos = new FileOutputStream(file, true);
        fos.write(documento.getBytes());
        fos.flush();
        fos.close();
    }
    
    private static String converter(Document document) throws TransformerException {
    	//Criamos uma instancia de Transformer
    	//O metodo setOutputProperty cria a formatacao
    	//ou nao do XML no arquivo. Se 'yes' entao formata,
    	//se 'no' entao escreve o arquivo em uma unica linha
    	Transformer transformer = TransformerFactory.newInstance().newTransformer();

    	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    	transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "DriveCETIPDOC.dtd");

    	//inicializar StreamResult para gravar para String
    	StreamResult result = new StreamResult(new StringWriter());
    	DOMSource source = new DOMSource((Node) document);
    	transformer.transform(source, result);

    	//Recupera o conteudo em formato String
    	String xmlString = result.getWriter().toString();

    	//Exibe o resultado da transformacao
    	System.out.append(xmlString);

    	//devolve a string
    	return xmlString;
    }
    
    
    private static void gerarXml(Contato contato) throws Exception {
    	//Criamos um nova instancia de DocumentBuilderFactory
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	//Usamos o metodo newDocumentBuilder() para
    	 //criar um nova instancia de DocumentBuilder
    	DocumentBuilder db = dbf.newDocumentBuilder();
    	//Obtem uma nova instancia de um Document
    	 //objeto para construir uma arvore com Document.
    	Document doc = (Document) db.newDocument();

    	//Cria a tag raiz do XML Contato
    	Element tagContato = doc.createElement("Contato");

    	Element idContato = doc.createElement("id");
    	Element nomeContato = doc.createElement("nome");
    	Element emailContato = doc.createElement("email");

    	//Insere os valores nas tags de contato
    	idContato.setTextContent(String.valueOf(contato.getId()));
    	nomeContato.setTextContent(contato.getNome());
    	emailContato.setTextContent(contato.getEmail());

    	//Insere na tag Contato as tags id, nome e email
    	tagContato.appendChild(idContato);
    	tagContato.appendChild(nomeContato);
    	tagContato.appendChild(emailContato);

    	//Cria a tag Telefones
    	Element tagFones = doc.createElement("Telefones");
    	//Cria a tag filha Telefone
    	Element tagFone = null;
    	//Cria as tags de Telefone
    	Element idFone;
    	Element dddFone;
    	Element numeroFone;

    	//Inserir a lista de telefones
    	for (Telefone fone : contato.getTelefones()) {
    		tagFone = doc.createElement("Telefone");

    		idFone = doc.createElement("id");
    		dddFone = doc.createElement("ddd");
    		numeroFone = doc.createElement("numero");
    		//Insere os valores de telefones nas tags referentes
    		idFone.setTextContent(String.valueOf(fone.getId()));
    		dddFone.setTextContent(String.valueOf(fone.getDdd()));
    		numeroFone.setTextContent(String.valueOf(fone.getNumero()));
    		//Insere as tags Telefone na tag Telefone
    		tagFone.appendChild(idFone);
    		tagFone.appendChild(dddFone);
    		tagFone.appendChild(numeroFone);
    		//Insere a tag Telefone na tag pai Telefones
    		tagFones.appendChild(tagFone);
    	}

    	//Inserer a tag telefones na tag Contato
    	tagContato.appendChild(tagFones);

    	//Cria a tag Endereco
    	Element tagEndereco = doc.createElement("Endereco");
    	//Cria as tags de Endereco
    	Element idEnd = doc.createElement("id");
    	Element logradouroEnd = doc.createElement("logradouro");
    	Element bairroEnd = doc.createElement("bairro");
    	Element cepEnd = doc.createElement("cep");
    	Element cidadeEnd = doc.createElement("cidade");
    	Element complementoEnd = doc.createElement("complemento");
    	Element numeroEnd = doc.createElement("numero");

    	//Inserer os dados na nas tags de Endereco
    	idEnd.setTextContent(String.valueOf(contato.getEndereco().getId()));
    	logradouroEnd.setTextContent(contato.getEndereco().getLogradouro());
    	bairroEnd.setTextContent(contato.getEndereco().getBairro());
    	cepEnd.setTextContent(contato.getEndereco().getCep());
    	cidadeEnd.setTextContent(contato.getEndereco().getCidade());
    	complementoEnd.setTextContent(contato.getEndereco().getComplemento());
    	numeroEnd.setTextContent(String.valueOf(contato.getEndereco().getNumero()));

    	//Insere as tags de Endereco na tag Endereco
    	tagEndereco.appendChild(idEnd);
    	tagEndereco.appendChild(logradouroEnd);
    	tagEndereco.appendChild(bairroEnd);
    	tagEndereco.appendChild(cepEnd);
    	tagEndereco.appendChild(cidadeEnd);
    	tagEndereco.appendChild(complementoEnd);
    	tagEndereco.appendChild(numeroEnd);

    	//Insere a tag Endereco na tag Contato
    	tagContato.appendChild(tagEndereco);

    	//Insere a tag Contato com as demais tags no objeto Document doc
    	doc.appendChild(tagContato);

    	//vamos converter o objeto doc
    	//em String para salvar no arquivo
    	String arquivo = converter(doc);

    	//vamos agora salvar o arquivo xml
    	salvarArquivo(arquivo);
    }
    
    public static void main(String[] args) {
    	Telefone residencial = new Telefone();
    	residencial.setId(1);
    	residencial.setDdd(55);
    	residencial.setNumero(32214512);

    	Telefone celular = new Telefone();
    	celular.setId(2);
    	celular.setDdd(55);
    	celular.setNumero(99879885);

    	Collection<Telefone> telefones = new ArrayList<Telefone>();
    	telefones.add(residencial);
    	telefones.add(celular);

    	Endereco endereco = new Endereco();
    	endereco.setId(11);
    	endereco.setLogradouro("Rua dos Javanezes");
    	endereco.setBairro("Largo Zero");
    	endereco.setCep("97010600");
    	endereco.setCidade("Java City");
    	endereco.setNumero(65);
    	endereco.setComplemento("Ap.103A");

    	Contato contato = new Contato();
    	contato.setId(100);
    	contato.setNome("Fulano da Silva");
    	contato.setEmail("fulano@email.com");
    	contato.setEndereco(endereco);
    	contato.setTelefones(telefones);

    	try {
    		gerarXml(contato);
    		//Contato c = lerXml();
    		//System.out.println(c.toString());
    	} catch (ParserConfigurationException e) {
    		e.printStackTrace();
    	} catch (TransformerException e) {
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

}
