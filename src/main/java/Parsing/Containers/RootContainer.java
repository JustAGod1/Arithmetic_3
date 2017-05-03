package Parsing.Containers;


import Stuff.Elments.Element;

/**
 * Created by Yuri on 23.10.16.
 */
public class RootContainer implements Container {

    Container index;
    Container base;

    public RootContainer(Container index, Container base) {

        this.index = index;
        this.base = base;
    }

    @Override
    public Element toElement() {
        //return new RootElement(base.toElement(), (Index) index.toElement());
        return null; // TODO: 06.12.16 Добавить Корень и пофиксить этот метод.
    }
}
