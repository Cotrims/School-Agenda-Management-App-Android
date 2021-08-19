package br.cotuca.agendaaluno;

public class Aluno
{
    String ra, nome, email;

    public String getRa() { return this.ra; }
    public String getNome() { return this.nome; }
    public String getEmail() { return this.email; }

    public void setRa(String ra) throws Exception
    {
        if(ra == null || ra.equals(""))
            throw new Exception("Ra vazio ou nulo");
        this.ra = ra;
    }

    public void setNome(String nome) throws Exception
    {
        if(nome == null || nome.equals(""))
            throw new Exception("Nome vazio ou nulo");
        this.nome = nome;
    }

    public void setEmail(String email) throws Exception
    {
        if(email == null || email.equals(""))
            throw new Exception("Email vazio ou nulo");
        this.email = email;
    }

    public Aluno(){}

    public Aluno(String ra, String nome, String email) throws Exception
    {
        setRa(ra);
        setNome(nome);
        setEmail(email);
    }

    public String toString()
    {
        String ret = "Aluno: { + \n";

        ret += "RA: " + getRa() + "\n";
        ret += "Nome: " + getNome() + "\n";
        ret += "Email: " + getEmail();

        return ret + "}";
    }

    public boolean equals(Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (this.getClass()!=obj.getClass())
            return false;

        Aluno aluno = (Aluno) obj;

        return (getRa().equals(aluno.getRa()) &&
                getNome().equals(aluno.getNome()) &&
                getEmail().equals(aluno.getEmail()));
    }

    public int HashCode()
    {
        int ret = 0;

        ret = 13 * ret + getRa().hashCode();
        ret = 13 * ret + getEmail().hashCode();
        ret = 13 * ret + getNome().hashCode();

        if(ret < 0) ret = -ret;

        return ret;
    }

    public Aluno (Aluno modelo) throws Exception
    {
        if (modelo==null)
            throw new Exception ("Modelo ausente");

        this.ra = modelo.ra;
        this.nome = modelo.nome;
        this.email = modelo.email;
    }

    public Object clone ()
    {
        Aluno ret=null;

        try
        {
            ret = new Aluno(this);
        }
        catch (Exception erro)
        {} // sei que this NUNCA ? null e o contrutor de copia da erro quando seu parametro ? null

        return ret;
    }
}