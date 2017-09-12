import java.util.ArrayList;
import java.util.Collection;

public class Contato {
	private int id;
	private String nome;
	private String email;
	private Endereco endereco;
	private Collection<Telefone> telefones = new ArrayList<Telefone>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Collection<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(Collection<Telefone> telefones) {
		this.telefones = telefones;
	}

	@Override
	public String toString() {
		return "Contato [nome=" + nome + ", email=" + email + ", endereco=" + endereco + ", telefones=" + telefones
				+ "]";
	}

	
	
	
}
