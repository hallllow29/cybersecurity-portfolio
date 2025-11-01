
package lib;

public class Node <T> {

	private T data;
	private Node <T> next;

	public Node(){
		next = null;
		this.data = null;
	}


	public Node(T data) {
		next = null;
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public Node <T> getNext() {
		return next;
	}

	public void setData (T data) {
		this.data = data;
	}

	public void setNext (Node <T> next) {
		this.next = next;
	}

	@Override
	public String toString(){
		return "(" + data + ")" ;
	}

	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node that = (Node) obj;
        return data.equals(that.data);
    }
}

