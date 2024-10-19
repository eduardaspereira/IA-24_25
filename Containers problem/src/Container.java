import java.util.*;


class Container implements Ilayout, Cloneable{
    private List<Stack<Character>> containers;
    private HashMap<Character,Integer> cDetails;
    private int cost;

    // Default constructor
    public Container(String config) throws IllegalStateException {
        containers= new ArrayList<>();
        cDetails= new HashMap<>();
        this.cost=0;
        String [] str= config.split(" ");
        for(String st: str){
            Stack<Character> stack = new Stack<>();
            for (int i = 0; i < st.length(); i++) {
                char ch= st.charAt(i);

                if(i+1 < st.length() && Character.isDigit(st.charAt(i+1))){
                    cDetails.put(ch, Character.getNumericValue(st.charAt(i+1)));
                i++;
                }
                else {
                    cDetails.put(ch,1);
                }
                stack.push(ch);
            }
            containers.add(stack);
        }
    }

    public Container(List<Stack<Character>> containers, HashMap<Character, Integer> cDetails) {
        this.containers = containers;
        this.cDetails= new HashMap<>(cDetails);
        this.cost = 0;
    }

    public Container clone() {
        return new Container(containers, cDetails);
    }

    @Override
    public List<Ilayout> children() {
        List<Ilayout> children = new ArrayList<>();

        for (int i = 0; i < containers.size(); i++) {
            Stack<Character> current = containers.get(i);

            if (!current.isEmpty()) {
                char container = current.peek();
                int cCost = cDetails.get(container);

                List<Stack<Character>> copia = deepCopy(i, -1);
                copia.get(i).pop();
                if(copia.get(i).isEmpty())
                    copia.remove(i);
                Stack<Character> chao = new Stack<>();
                chao.push(container);
                copia.add(chao);

                Container c1 = new Container(copia, cDetails);
                c1.cost= cCost;
                children.add(c1);

                for (int j = 0; j < containers.size(); j++) {
                    if(i!= j){
                        List<Stack<Character>> copia2 = deepCopy(i,j);

                        copia2.get(i).pop();
                        copia2.get(j).push(container);

                        if(copia2.get(i).isEmpty())
                            copia2.remove(i);

                        Container c2 = new Container(copia2,cDetails);
                        c2.cost= cCost;
                        children.add(c2);
                    }
                }
            }
        }
        return children;
    }

    @SuppressWarnings("unchecked")
    public List<Stack<Character>> deepCopy(int iFonte, int iDest){
        List<Stack<Character>> stacks = new ArrayList<>(containers);

        if(iFonte >= 0)
            stacks.set(iFonte, (Stack<Character>) stacks.get(iFonte).clone());
        if(iDest >= 0)
            stacks.set(iDest, (Stack<Character>) stacks.get(iDest).clone());
        return stacks;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Verifica se são o mesmo objeto
        if (obj == null) return false;

        Container c1 = (Container) obj;

        if(this.containers.size() != c1.containers.size())
            return false;
        if(this.hashCode() == c1.hashCode())
            return true;
        return Objects.equals(containers, c1.containers);
    }

    public void ordenaC(){
        containers.sort((s1,s2)->{
            char sChao1 = s1.firstElement();
            char sChao2 = s2.firstElement();
            return Character.compare(sChao1, sChao2);
        });
    }

    @Override
    public String toString() {
        ordenaC();
        StringBuilder sb = new StringBuilder();
        for (Stack<Character> c : containers) {
            if (!c.isEmpty())
                sb.append(c.toString()+ "\n");
        }
        return sb.toString();
    }
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean isGoal(Ilayout l) {
        return this.equals(l);
    }


    public int getK() {
        return cost;
    }
}
