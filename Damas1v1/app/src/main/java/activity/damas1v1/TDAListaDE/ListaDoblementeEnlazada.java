package activity.damas1v1.TDAListaDE;

import java.io.Serializable;
import java.util.Iterator;

public class ListaDoblementeEnlazada<E> implements PositionList<E>, Serializable {
	private DNode<E> header;
	private DNode<E> trailer;
	private int longitud;
	
	//Constructor:
	public ListaDoblementeEnlazada() {
		header = new DNode<E>(null, null, null);
		trailer = new DNode<E>(header, null, null);
		header.setSiguiente(trailer);
		longitud = 0;
	}
	
	
	
	//Comandos:
	public void addFirst(E e) {
		longitud++;
		DNode<E> nuevoNodo = new DNode<E>(header, header.getSiguiente(), e);
		header.getSiguiente().setAnterior(nuevoNodo);
		header.setSiguiente(nuevoNodo);
	}
	
	public void addLast(E e) {
		longitud++;
		DNode<E> nuevoNodo = new DNode<E>(trailer.getAnterior(), trailer, e);
		trailer.getAnterior().setSiguiente(nuevoNodo);
		trailer.setAnterior(nuevoNodo);
	}
	
	public void addAfter(Position<E> p, E e) throws InvalidPositionException {
		DNode<E> v = checkPosition(p);
		longitud++;
		DNode<E> nuevoNodo = new DNode<E>(v, v.getSiguiente(), e);
		v.getSiguiente().setAnterior(nuevoNodo);
		v.setSiguiente(nuevoNodo);
	}
	
	public void addBefore(Position<E> p, E e) throws InvalidPositionException {
		DNode<E> v = checkPosition(p);
		longitud++;
		DNode<E> nuevoNodo = new DNode<E>(v.getAnterior(), v, e);
		v.getAnterior().setSiguiente(nuevoNodo);
		v.setAnterior(nuevoNodo);
	}
	
	public E set(Position<E> p, E element) throws InvalidPositionException{
		DNode<E> v = checkPosition(p);
		E viejoElem = v.element();
		v.setElemento(element);
		return viejoElem;
	}
	
	public void remove(Position<E> p) throws InvalidPositionException {
		DNode<E> v = checkPosition(p);
		longitud--;
		
		DNode<E> vAnt = v.getAnterior();
		DNode<E> vSig = v.getSiguiente();
		vAnt.setSiguiente(vSig);
		vSig.setAnterior(vAnt);
		
		//Invalidar la posición y quitarla de la lista.¿
		v.setSiguiente(null);
		v.setAnterior(null);
	}
	
	
	
	//Consultas:
	public int size() {
		return longitud;
	}
	
	public boolean isEmpty() {
		return longitud == 0;
	}
	
	public Position<E> first() throws EmptyListException {
		if(isEmpty())
			throw new EmptyListException("Lista vacía.");
		return header.getSiguiente();
	}
	
	public Position<E> last() throws EmptyListException {
		if(isEmpty())
			throw new EmptyListException("Lista vacía.");
		return trailer.getAnterior();
	}
	
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNode<E> n = checkPosition(p);
		if(n.getSiguiente() == trailer)
			throw new BoundaryViolationException("La posición solicitada supera al rango comprendido en la lista.");
		return n.getSiguiente();
	}
	
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNode<E> v = checkPosition(p);
		DNode<E> prev = v.getAnterior();
		if(prev == header)
			throw new BoundaryViolationException("La posición pasada por parámetro en prev() no tiene anterior por ser la primera.");
		return prev;
	}


	private DNode<E> checkPosition(Position<E> p) throws InvalidPositionException {
		if (p == null)
			throw new InvalidPositionException("Posición nula.");
		if (p == header)
			throw new InvalidPositionException("Posición inválida correspondiente al nodo header.");
		if (p == trailer)
			throw new InvalidPositionException("Posición inválida correspondiente al nodo trailer.");
		
		try {
			DNode<E> temp = (DNode<E>) p;
			if (temp.getAnterior() == null || temp.getSiguiente() == null)
				throw new InvalidPositionException("La posición no pertenece a una Lista Doblemente Enlazada.");
			return temp;
		}
		catch(ClassCastException e) {
			throw new InvalidPositionException("Posición de tipo erróneo para esta Lista Doblemente Enlazada.");
		}
	}
	
	
	public Iterator<E> iterator(){
		try {
			return new ElementIterator<E>(this);
		}
		catch (EmptyListException exc) {
			System.out.println(exc.getMessage() + "\n");
			exc.printStackTrace();
			//noinspection ConstantConditions
			return null;
		}
	}
	
	public Iterable<Position<E>> positions(){
		try {
			PositionList<Position<E>> P = new ListaDoblementeEnlazada<Position<E>>();
			if (!isEmpty()) {
				Position<E> p = first();
				while(true) {
					P.addLast(p);
					if (p == last())
						break;
					p = next(p);
				}
			}
			return P;
		}
		catch(EmptyListException | InvalidPositionException | BoundaryViolationException exc) {
			System.out.println(exc.getMessage() + "\n");
			exc.printStackTrace();
			return null;
		}
	}
}