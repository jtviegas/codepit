import groovy.transform.*

@ToString(includeNames = true) 
@EqualsAndHashCode //abstract syntax tree transformer
@Canonical
class Person{
    String first
   String last
   
   //String toString(){"$first $last"}
}

Person qb = new Person(first: 'v', last: 'ff')
Person qb2 = new Person(first: 'v', last: 'ff')
Person qb3 = new Person( 'v2', 'ff2')
//qb.setFirst('aa')
//qb.last = 'bb'
println "${qb.getFirst()} ${qb.last}"
println qb.toString()
println qb == qb2
println qb3.toString()

def nums = [3,4,5,3,5,7] as SortedSet
println nums
nums = [3,4,5,3,5,7]
for(int i=0;i<nums.size();i++) {println nums[i]}
for(n in nums) {println n}
//closure
nums.each { println it }
nums.each { n -> println n }
nums.eachWithIndex{ n,i -> println "nums[$i] = $n" }

println nums.collect { it*2 }

def cities = ['boston', 'new york', 'Cairo']
println cities.collect { it.toLowerCase().reverse() }

println nums.collect { it*2 }.findAll{ it%3 == 0 }
println nums.collect { it*2 }.findAll{ it%3 == 0 }.sum()

def map=[a:1, b:2, c:3]

map.d = 4
map['e'] = 7
map.put('f',5)
println map
println map.keySet()
println map.values()
println map.entrySet()

map.each { o -> println "${o.getKey()}=${o.getValue()}" }
map.each { o -> println "${o.key}=${o.value}" }
map.each { k,v -> println "$k=$v" }
map.collect { k,v -> k*v }
