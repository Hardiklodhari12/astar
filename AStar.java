import java.util.*;
public class AStar
{
    static class Board {
        int a[][] = new int[3][3];
        int steps,heuristic,x,y;
        Board(int a[][],int x,int y,int steps,int heuristic){
            this.x=x;
            this.y=y;
            this.steps=steps;
            this.heuristic=heuristic;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    this.a[i][j]=a[i][j];
                }
            }
        }

        public String toString(){
            StringBuffer s = new StringBuffer();
            for(int i=0;i<3;i++)
                for(int j=0;j<3;j++)
                    s.append(a[i][j]);
            return new String(s);
        }
    }
    static int[][] goalindices=new int[9][2];
    static int goal[][] = {
            {1,6,2},
            {5,8,0},
            {7,4,3}  
        };
    static int manhattanDistance(int cur[][])
    {
        int sum = 0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                sum+=Math.abs(i-goalindices[cur[i][j]][0]) + Math.abs(j-goalindices[cur[i][j]][1]);
            }
        }
        return sum;
    }
    static int misplacedTiles(int cur[][])
    {
        int sum = 0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(cur[i][j]!=goal[i][j])
                    sum++;
            }
        }
        return sum;
    }
    public static void main(String args[])
    {
        int i,j,k;
        int initial[][] = {
            {1,0,2},
            {5,6,3},
            {7,8,4}
        };
        
        PriorityQueue<Board> open = new PriorityQueue<>(new Comparator<Board>(){
            public int compare(Board b1, Board b2){
                if((b1.steps+b1.heuristic)==(b2.steps+b2.heuristic)) return 0;
                return (b1.steps+b1.heuristic)>(b2.steps+b2.heuristic)?1:-1;
            }
        });
        HashMap<String,Integer> closed = new HashMap<>();
        
        for(j=0;j<3;j++)
        {
            for(k=0;k<3;k++)
            {
                goalindices[goal[j][k]][0]=j;
                goalindices[goal[j][k]][1]=k;
            }
        }
        Board initialState = new Board(initial,0,1,0,manhattanDistance(initial));
        //System.out.println("Initial state: "+initialState.a[initialState.x][initialState.y]+" "+initialState.x+" "+initialState.y);
        open.add(initialState);
        Board q,next;
        int s1[] = new int[]{1,-1,0,0},s2[]=new int[]{0,0,1,-1},temp[][]=new int[3][3],t,md,flag=10,mt;
        //System.out.println(initialState.a[0][1]);
        while(!open.isEmpty()){
            q = open.remove();
            // if(flag-->0)
            //     System.out.println(q.a[q.x][q.y]+" "+q.x+" "+q.y);
            if(q.heuristic == 0){
                System.out.println("Reached goal state in "+q.steps+" steps.");
                break;
            }
            closed.put(q.toString(),q.steps+q.heuristic);
            for(i=0;i<3;i++){
                for(j=0;j<3;j++)
                    temp[i][j]=q.a[i][j];
            }
            for(i = 0; i < 4; i++) {
                if((q.x+s1[i])>=0&&(q.x+s1[i])<3&&(q.y+s2[i])>=0&&(q.y+s2[i])<3){
                    t=temp[q.x][q.y];
                    temp[q.x][q.y]=temp[q.x+s1[i]][q.y+s2[i]];
                    temp[q.x+s1[i]][q.y+s2[i]]=t;
                    md = manhattanDistance(temp);
                    // mt = misplacedTiles(temp);
                    next=new Board(temp,q.x+s1[i],q.y+s2[i],1+q.steps,md);
                    t=temp[q.x][q.y];
                    temp[q.x][q.y]=temp[q.x+s1[i]][q.y+s2[i]];
                    temp[q.x+s1[i]][q.y+s2[i]]=t;
                    String stateString = next.toString();
                    if(closed.containsKey(stateString)) {
                        if(closed.get(stateString)>md+next.steps){
                            closed.remove(stateString);
                            //System.out.println("Added: "+next);
                            open.add(next);
                        }
                    }
                    else{
                        //System.out.println("Added: "+next);
                        open.add(next);
                    }
                }
            }
        }
    }
}
