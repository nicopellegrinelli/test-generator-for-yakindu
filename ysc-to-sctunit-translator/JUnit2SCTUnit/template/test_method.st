test_method(test_name) ::= 
<<@Test
operation <test_name> () {
    enter
	<raise_event("event1")>
	<proceed_cycle()>
	<assert_state("stateA")>
}
>>