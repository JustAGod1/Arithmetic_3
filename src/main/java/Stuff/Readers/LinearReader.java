package Stuff.Readers;


import Stuff.Elments.GostlyElements.ErrorElement;
import Stuff.Elments.Element;
import Stuff.Elments.MultElement;
import Stuff.Elments.UnknownElement;
import Stuff.Enums.EquationSide;
import Stuff.Utilities.Stepper;

import java.util.*;

/**
 * Created by Yuri on 06.12.16.
 */
public class LinearReader implements Iterable<Element>, Collection<Element> {

    private ArrayList<Element> elements = new ArrayList<>();
    private int index = 0;

    public LinearReader(ArrayList<Element> elements) {
        this.elements = elements;
    }

    public LinearReader() {
    }

    public LinearReader(Element element) {
        elements.add(element);
    }

    public boolean hasNextUpdatable() {
        for (int i = index; i < size(); i++) {
            if (get(i).getPriority() != -1) return true;
        }
        return false;
    }

    public boolean isHas(Class<? extends Element> type) {
        for (Element element : this) {
            if (element.getClass().getName().equals(type.getName())) return true;
        }
        return false;
    }

    public boolean hasNext(EquationSide side) {
        for (int i = index; i < size(); i++) {

            if (get(i).getPreferredSide() == side) return true;
        }
        return false;
    }

    public boolean hasNext() {
        return index < elements.size();
    }

    public boolean hasNext(Element element) {
        for (int i = index; i < elements.size(); i++) {
            if (get(i).isSimilar(element)) return true;
        }
        return false;
    }

    public Element getNextUpdatable() {
        if (!hasNextUpdatable()) return new ErrorElement("У меня нету следующего обновляемого");

        int max = Integer.MIN_VALUE;

        for (int i = 0; i < size(); i++) {
            if ((get(i).getPriority() != -1) || (get(i).getPriority() > max)) {
                max = get(i).getPriority();
            }
        }

        for (int i = index; i < size(); i++) {
            index++;
            if (get(i).getPriority() == max) return get(i);
        }

        return new ErrorElement("У меня нету следующего обновляемого");
    }

    public Element getNext(EquationSide side) {
        if (!hasNext(side)) return new ErrorElement("Следующего нету");

        for (int i = index; i < size(); i++) {
            index++;
            if (get(i).getPreferredSide() == side) return get(i);
        }

        return new ErrorElement("Следующего нету");
    }

    public Element getNext(Element element) {

        for (int i = index; i < elements.size(); i++) {
            if (get(i).isSimilar(element)) return get(i);
        }

        return new ErrorElement("Следующего нету");
    }

    public Element getNext() throws IndexOutOfBoundsException {
        Element element = get(index);
        index++;
        return element;
    }

    public boolean isHasUnknown() {
        for (Element element : this) {
            if (element instanceof UnknownElement) return true;
        }
        return false;
    }

    public boolean isHasMultipling() {
        for (Element element : this) {
            if (element instanceof MultElement) return true;
        }
        return false;
    }

    public boolean isAlreadyDecided() {
        return !hasNextUpdatable();
    }

    public boolean isHasSimiliars() {
        int indexBefore = index;
        for (Element element : this) {
            reset();

            if (hasNext(element))
                return true;
        }
        index = indexBefore;

        return false;
    }

    public boolean executeAddition(Stepper stepper) {
        reset();
        if (!hasNext()) return false;

        boolean res = false;
        Element element1 = getNext();
        Element element2;

        while (hasNext(element1)) {


            element2 = getNext(element1);
            if (element1 == element2) return res;
            if (element2 instanceof ErrorElement) return res;
            remove(element1);
            remove(element2);
            element1 = element1.add(element2, stepper);
            add(element1);

            stepper.step();
            res = true;
        }

        if (!hasNext()) return res;
        element1 = getNext();

        while (hasNext(element1)) {
            remove(element1);

            element2 = getNext(element1);
            element1 = element1.add(element2, stepper);
            remove(element2);

            stepper.step();
            res = true;
        }

        return res;
    }

    public boolean executeUpdate(Stepper stepper) {
        Element element1;
        Element[] elementss;
        boolean res = false;
        reset();
        while (hasNextUpdatable()) {
            element1 = getNextUpdatable();
            int index = indexOf(element1);
            elementss = element1.update(stepper);

            if (!element1.equals(elementss[0])) {
                remove(element1);
                addAll(index, Arrays.asList(elementss));
                stepper.step();
                res = true;
            }

        }
        return res;
    }

    public void addAll(int index, Collection<? extends Element> ts) {
        elements.addAll(index, ts);
    }

    public void addLinearReader(LinearReader lr) {
        for (Element element : lr) {
            add(element);
        }
    }
    
    public void executeMultiplying(Stepper stepper, Element multiplier) {
        Element element;
        reset();
        while (hasNext()) {
            element = getNext();
            int index = indexOf(element);
            elements.remove(element);
            elements.add(index, element.multBy(multiplier, stepper));
        }
    }

    public void executeDividing(Stepper stepper, Element divider) {
        Element element;
        reset();
        while (hasNext()) {
            element = getNext();
            int index = indexOf(element);
            elements.remove(element);
            add(index, element.divideBy(divider, stepper));
        }
    }

    public String rendToString(boolean needMark) {
        StringBuilder builder = new StringBuilder();

        try {
            builder.append(get(0).rendToString(false, needMark)).append(' ');
        } catch (IndexOutOfBoundsException e) {
            return "0";
        }

        for (int i = 1; i < size(); i++) {
            builder.append(get(i).rendToString(false, true));
        }

        return builder.toString().trim();
    }

    public void reset() {
        index = 0;
    }

    public Element get(int index) throws IndexOutOfBoundsException {
        try {
            return elements.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return elements.contains(o);
    }

    @Override
    public Iterator<Element> iterator() {
        return elements.iterator();
    }

    @Override
    public Element[] toArray() {
        Element[] els = new Element[elements.size()];

        for (int i = 0; i < elements.size(); i++) {
            els[i] = elements.get(i);
        }

        return els;
    }

    public ArrayList<Element> getArray() {
        return elements;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return elements.toArray(a);
    }

    @Override
    public boolean add(Element element) {
        return elements.add(element);
    }

    @Override
    public boolean remove(Object o) {
        int i = elements.indexOf(o);
        if ((i <= index) && (index > 0)) index--;


        return elements.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return elements.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Element> c) {
        return elements.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return elements.retainAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return elements.retainAll(c);
    }

    @Override
    public void clear() {
        elements.clear();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        try {
            result.append(elements.get(0).rendToString(false, false)).append(' ');
        } catch (IndexOutOfBoundsException e) {return "0";}

        for (int i = 1; i < elements.size(); i++) {
            result.append(elements.get(i).rendToString(false, true)).append(' ');
        }

        return result.toString().trim();
    }

    public String toHTML(boolean needTextTag) {
        StringBuilder result = new StringBuilder();

        try {
            String tmp = elements.get(0).rendToHTML(false, false, needTextTag);
            result.append(tmp).append(' ');
        } catch (IndexOutOfBoundsException e) {
            return "<text>0</text>";
        }

        for (int i = 1; i < elements.size(); i++) {
            result
                    .append(elements.get(i).rendToHTML(false, true, needTextTag));
        }

        return result.toString();
    }

    @Override
    public LinearReader clone() {
        ArrayList<Element> elements = new ArrayList<>();

        for (Element element : this.elements) {
            elements.add(element.clone());
        }
        return new LinearReader(elements);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LinearReader) {
            LinearReader reader = (LinearReader) obj;

            return reader.elements.equals(elements);
        } else {
            return false;
        }
    }

    public void set(int index, Element element) {
        elements.set(index, element);
    }

    public void replace(Element before, Element after) {
        int index = this.indexOf(before);
        this.remove(index);
        this.add(index, after);
    }

    public void add(int index, Element element) {
        elements.add(index, element);
    }

    public int indexOf(Element element) {
        return elements.indexOf(element);
    }
}
