package lib.lists;

import lib.DoubleNode;

import java.util.ArrayList;
import java.util.List;

public class DoublyLinkedList<T> {

    private DoubleNode<T> head;
    private DoubleNode<T> tail;
    private int count;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.count = 0;
    }

    public int getCount() {
        return this.count;
    }

    public void addNodeFront(T node) {
        DoubleNode<T> temp = new DoubleNode<T>(node);

        if (head != null) {
            //Ele faz o next mete o head que esta neste momento
            temp.setNext(head);

            //A cabeça passa a ser o novo node adicionado
            head.setPrev(temp);

        }

        head = temp;

        //Se a cauda tiver null entao so tem um elemento a lista e esse elemento tanto aponta para a cauda como para a cabeça
        if (tail == null) {
            tail = temp;

        }

        this.count++;
    }

    public void removeFrontNode() {

        //Se a lista estiver vazia ele avisa que nao pode remover nada porque nao tem nada para remover
        if (head == null) {
            System.out.println("List is empty no node to remove");
            return;
        }

        //Se a lista so tem um nó então tanto a cabeça como a cauda vao ser iguais logo mete os dois a null
        if (head.getNext() == null) {
            head = null;
            tail = null;
        } else {
            //Se tiver mais nodes atualiza a cabeça para o proximo node e mete o node anterior ao da cabeça atual a null
            head = head.getNext();
            head.setPrev(null);
        }

        this.count--;
        System.out.println("Front node removed");

    }

    public void removeLastNode() {
        if (head == null) {
            System.out.println("List empty");
            return;
        }

        if (head.getNext() == null) {
            tail = null;
            head = null;
        } else {
            tail = tail.getPrev();
            tail.setNext(null);
        }

        this.count--;
        System.out.println("Last node removed");

    }

    public void printList() {
        DoubleNode<T> current = head;

        while (current != null) {
            System.out.println(current.toString());
            current = current.getNext();
        }

        System.out.println("List ended");

    }

    public boolean isListEmpty() {
        if (this.count > 0) {
            return false;
        }

        return true;
    }

    public List<T> getArrayList() {
        ArrayList<T> elements = new ArrayList<>();

        DoubleNode<T> current = head;

        while (current != null) {
            elements.add(current.getElement());
            current = current.getNext();
        }

        return elements;

    }

    public List<T> getArrayTillGivenPosition(int position) {
        ArrayList<T> elements = new ArrayList<>();

        DoubleNode<T> current = head;

        int currentPosition = 0;

        if (position < 0 || position > this.count) {
            throw new IllegalArgumentException("Invalid position");
        }

        while (current != null && currentPosition < position) {
            elements.add(current.getElement());
            current = current.getNext();
            currentPosition++;

        }

        return elements;

    }

    public List<T> getArrayAfterGivenPosition(int position) {
        ArrayList<T> elements = new ArrayList<>();

        DoubleNode<T> current = head;

        int currentPosition = 0;

        // Verifica se a posição é válida
        if (position < 0 || position >= this.count) {
            throw new IllegalArgumentException("Invalid position");
        }

        // Avança até a posição fornecida
        while (current != null && currentPosition < position) {
            current = current.getNext();
            currentPosition++;
        }

        // Começa a adicionar os elementos após a posição fornecida
        if (current != null) {
            current = current.getNext(); // Pula o elemento da posição fornecida
        }

        while (current != null) {
            elements.add(current.getElement());
            current = current.getNext();
        }

        return elements;
    }

    public DoublyLinkedList<Integer> getAllPairElements() {
        DoublyLinkedList<Integer> pairsList = new DoublyLinkedList<>();
        DoubleNode<T> current = head;

        while (current != null) {
            if (current.getElement() instanceof Integer) {
                Integer value = (Integer) current.getElement();
                if (value % 2 == 0) {
                    pairsList.addNodeFront(value);
                }
            }
            current = current.getNext();
        }

        return pairsList;
    }

    public int removeAndCountEqualElements(T element) {
        DoubleNode<T> current = head;
        int numberOfEquals = 0;

        while (current != null) {
            if (current.getElement().equals(element)) {
                numberOfEquals++;

                if (current == head) {
                    head = head.getNext();
                    if (head != null) {
                        head.setPrev(null);
                    }
                } else {
                    if (current.getPrev() != null) {
                        current.getPrev().setNext(current.getNext());
                    }
                    if (current.getNext() != null) {
                        current.getNext().setPrev(current.getPrev());
                    }
                }
                if (current.getNext() == null) {
                    tail = current.getPrev();
                    if (tail != null) {
                        tail.setNext(null);
                    }
                }
                count--;

                current = (current.getNext() != null) ? current.getNext() : null;
            } else {
                current = current.getNext();
            }
        }

        return numberOfEquals;
    }

    public String printFirst() {
        return this.printFromBegin(head);
    }

    private String printFromBegin(DoubleNode<T> node) {
        if (node == null) {
            return "";
        }

        return node.getElement() + "\n" + this.printFromBegin(node.getNext());
    }

    public String printReverse() {
        return this.printFromEnd(this.tail);
    }

    private String printFromEnd(DoubleNode<T> node) {
        if (node == null) {
            return "";
        }

        return node.getElement() + "\n" + this.printFromEnd(node.getPrev());
    }

}
