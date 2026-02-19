package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class  Task9 {

  private long count;

  // Before: this void has a bag with
  // skip first element
  public List<String> getNames(List<Person> persons) {
    return persons.stream().skip(1).map(Person::firstName).collect(Collectors.toList());
  }

  // distinct names by HashSet, that is much better and faster than stream.distinct() because of hash tables
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }

  // string concatenation of person name, surname and middle name for frontend developers
  public String convertPersonToString(Person person) {
    return Stream.of(person.firstName(), person.secondName(), person.middleName())
            .filter(Objects::nonNull)// to convert null values to empty strings
            .collect(Collectors.joining(" "));
  }

  // Map with Person.id() -> Person::firstName
  // for loop is much slower than the stream and makes it harder to read
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
            .filter(person -> person.id() != null)
            .collect(Collectors.toMap(
                    Person::id,
                    Person::firstName,
                    (previous, newKey) -> previous
            ));
  }

  // are there any matching personalities in the two collections?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    // hash table search - O(1), but for nested loop for its O(nm)
    Set<Person> personSet = new HashSet<>(persons2);
    return persons1.stream()
            .anyMatch(personSet::contains);
  }

  // count even numbers
  public long countEven(Stream<Integer> numbers) {
    // count was created specifically for the tasks of getting the number
    return numbers.filter(num -> num % 2 == 0).count();
  }

  // Riddle - explain why assert is always true here
  // Explanation of the salt - we shuffled the numbers, wrapped them in a HashSet, and toString() returned them in sorted order.
  void listVsSet() {
    // the hash code in this case corresponds to the number itself (there are no additional conversions)
    // but on different jvms there may not be such a "randomness" with the order
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
