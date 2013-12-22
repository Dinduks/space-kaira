package fr.upem.spacekaira.shape.character;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Iterators {

     public static <E> Iterator<E> asIterator(List<List<E>> lists) {
         List<Iterator<E>> iterators = new ArrayList<>(lists.size());
         for(List<E> list : lists) {
             if(list != null)
                iterators.add(list.iterator());
         }

         return new Iterator<E> () {
            int current=0;
             public boolean hasNext() {
                 while ( current < iterators.size() && !iterators.get(current).hasNext() )
                     current++;

                 return current < iterators.size();
             }

             public E next() {
                 while ( current < iterators.size() && !iterators.get(current).hasNext() )
                     current++;

                 return iterators.get(current).next();
             }
         };
    }
}
