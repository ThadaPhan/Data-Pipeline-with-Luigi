namespace java Model

struct Person {
	1: string Id;
	2: string Name;
	3: string Gender;
	4: string Age
}

service Services {
	i32 nextPrimeNumber(1:i32 number),
	bool putToDatabase(1:Person person),
	bool putMultipleToDatabase(1:list<Person> people),
	Person getFromDatabase(1:string id),
	list<Person> getMultipleFromDatabase(1:list<string> ids)
}
