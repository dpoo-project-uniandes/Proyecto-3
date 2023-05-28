package services;

public class Dupla<T1, T2> {
	private T1 primero;
    private T2 segundo;

    public Dupla(T1 primero, T2 segundo) {
        this.primero = primero;
        this.segundo = segundo;
    }

    public T1 getPrimero() {
        return primero;
    }

    public void setPrimero(T1 primero) {
        this.primero = primero;
    }

    public T2 getSegundo() {
        return segundo;
    }

    public void setSegundo(T2 segundo) {
        this.segundo = segundo;
    }
}

