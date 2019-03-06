package activity.damas1v1.TDAListaDE;

import java.io.Serializable;

class DNode<E> implements Position<E>, Serializable {
	private E elemento;
	private DNode<E> siguiente;
	private DNode<E> anterior;
	
	//Constructor:
	DNode(DNode<E> ant, DNode<E> sig, E item) {
		elemento = item;
		siguiente = sig;
		anterior = ant;
	}
	
	
	
	//Comandos y Consultas:
	public E element() {
		return elemento;
	}
	
	void setElemento(E elemento) {
		this.elemento=elemento;
	}
	
	DNode<E> getSiguiente() {
		return siguiente;
	}
	
	void setSiguiente (DNode<E> siguiente) {
		this.siguiente = siguiente;
	}
	
	DNode<E> getAnterior() {
		return anterior;
	}
	
	void setAnterior (DNode<E> ant) {
		this.anterior = ant;
	}
}
