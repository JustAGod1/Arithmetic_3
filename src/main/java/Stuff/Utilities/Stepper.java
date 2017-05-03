package Stuff.Utilities;


import Stuff.Readers.Equation;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Yuri on 06.12.16.
 */
public class Stepper implements Iterable<Stepper.Step> {
    private static final Object monitor = new Object();
    private static ArrayList<Stepper> instances = new ArrayList<>();
    protected Equation equation;
    private ArrayList<Step> steps = new ArrayList<>();
    private Step currentStep = new Step();
    private boolean ignoreException;

    public Stepper(Equation equation) {
        this.equation = equation;
        instances.add(this);
    }

    public synchronized static void saveAll() {
        synchronized (monitor) {
            for (Stepper stepper : instances) {
                stepper.writeHTMLToFile();
            }
            System.out.println("Saved");
        }
    }

    public boolean step() {
        synchronized (monitor) {
            try {
                if (steps.size() != 0) {
                    if (steps.get(steps.size() - 1).stringEquation.equals(equation.toString())) return false;
                }
                currentStep.HTMLEquation = equation.toHTML();
                currentStep.stringEquation = equation.toString();

                steps.add(currentStep);
                currentStep = new Step();
            } catch (Exception e) {
                if (ignoreException) return false;
                System.err.println("Сбой во время сохранения шага");
                e.printStackTrace();
            }

        }
        /*try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return true;
    }

    public void setComment(String comment, boolean needHTMLTranslation) {
        currentStep.comment = comment;
        if (needHTMLTranslation) setHTMLComment(String.format("<font color=\"gray\">%s</font>", comment));
    }

    public void setHTMLComment(String comment) {
        currentStep.HTMLComment = comment;
    }

    public void writeToConsole() {
        for (Step step : steps) {
            System.out.println(step.toString());
        }
    }

    public String getHTML() {
        StringBuilder res = new StringBuilder();

        res.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=cp1251\" />");

        for (Step step : this) {
            res.append(step.toHTML());
        }

        return res.toString();
    }

    public void writeHTMLToFile(String s) {
        try {
            writeHTMLToFile(new FileOutputStream(s));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeHTMLToFile(FileOutputStream file) {


        DataOutputStream stream;



        stream = new DataOutputStream(file);
        try {
            stream.write(("<head>\n" +
                    "<meta charset=\"utf-8 \" />\n" +
                    "<title>Решение уравнения</title>\n" +
                    "</head>").getBytes());
            for (Step step : steps) {
                stream.write(step.toHTML().getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void writeHTMLToFile() {
        try {
            URL resourceUrl = getClass().getClassLoader().getResource("files/HTMLEquation.html");
            File file = new File(resourceUrl.getFile());
            FileOutputStream output = new FileOutputStream(file);
            writeHTMLToFile(output);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public Iterator<Step> iterator() {
        return steps.iterator();
    }

    public Equation getEquation() {
        return equation;
    }

    public Stepper setIgnoreException(boolean ignoreException) {
        this.ignoreException = ignoreException;

        return this;
    }

    public class Step {
        public String comment = "";
        public String HTMLComment = "";
        private String stringEquation = "empty";
        private String HTMLEquation = "empty";

        public Step(Equation equation) {
            this.stringEquation = equation.toString();
            this.HTMLEquation = equation.toHTML();
        }

        public Step(Equation equation, String comment) {
            this.stringEquation = equation.toString();
            this.HTMLEquation = equation.toHTML();
            this.comment = comment;
        }

        public Step() {

        }

        @Override
        public String toString() {
            return stringEquation + comment;
        }

        public String toHTML() {
            return String.format("<br>%s %s</br>", HTMLEquation, HTMLComment);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Step step = (Step) o;

            //if (comment != null ? !comment.equals(step.comment) : step.comment != null) return false;
            //if (HTMLComment != null ? !HTMLComment.equals(step.HTMLComment) : step.HTMLComment != null) return false;
            if (stringEquation != null ? !stringEquation.equals(step.stringEquation) : step.stringEquation != null)
                return false;
            return HTMLEquation != null ? HTMLEquation.equals(step.HTMLEquation) : step.HTMLEquation == null;
        }

        @Override
        public int hashCode() {
            int result = comment != null ? comment.hashCode() : 0;
            result = 31 * result + (HTMLComment != null ? HTMLComment.hashCode() : 0);
            result = 31 * result + (stringEquation != null ? stringEquation.hashCode() : 0);
            result = 31 * result + (HTMLEquation != null ? HTMLEquation.hashCode() : 0);
            return result;
        }
    }


}
