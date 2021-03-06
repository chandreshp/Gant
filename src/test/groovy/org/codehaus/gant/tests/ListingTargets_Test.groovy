//  Gant -- A Groovy way of scripting Ant tasks.
//
//  Copyright © 2006–2010, 2013  Russel Winder
//
//  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
//  compliance with the License. You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software distributed under the License is
//  distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
//  implied. See the License for the specific language governing permissions and limitations under the
//  License.

package org.codehaus.gant.tests

/**
 *  A test to ensure that the target listing works.
 *
 *  @author Russel Winder <russel@winder.org.uk>
 */
final class ListingTargets_Test extends GantTestCase {
  final coreScript = '''
target(something: "Do something.") { }
target(somethingElse: "Do something else.") { }
'''
  void testSomethingUsingP() {
    script = coreScript
    assertEquals(0, gant.processArgs(['-p',  '-f',  '-'] as String[]))
    assertEquals('''
 something      Do something.
 somethingElse  Do something else.

''', output)
  }
  void testSomethingAndCleanUsingP() {
    script = 'includeTargets << gant.targets.Clean\n' + coreScript
    assertEquals(0, gant.processArgs(['-p',  '-f',  '-'] as String[]))
    assertEquals('''
 clean          Action the cleaning.
 clobber        Action the clobbering. Do the cleaning first.
 something      Do something.
 somethingElse  Do something else.

''', output)
  }
  void testGStringsUsingP() {
    script = '''
def theWord = 'The Word'
target(something: "Do ${theWord}.") { }
target(somethingElse: "Do ${theWord}.") { }
'''
    assertEquals(0, gant.processArgs(['-p',  '-f',  '-'] as String[]))
    assertEquals('''
 something      Do The Word.
 somethingElse  Do The Word.

''', output)
  }
  void testDefaultSomethingUsingP() {
    script = '''
target(something: "Do something.") { }
target(somethingElse: "Do something else.") { }
target('default': "The default.") { something() }
'''
    assertEquals(0, gant.processArgs(['-p',  '-f',  '-'] as String[]))
    assertEquals('''
 default        The default.
 something      Do something.
 somethingElse  Do something else.

Default target is default.

''', output)
  }
  void testDefaultSomethingSetDefaultClosureUsingP() {
    script = '''
target(something: "Do something.") { }
target(somethingElse: "Do something else.") { }
setDefaultTarget(something)
'''
    assertEquals(0, gant.processArgs(['-p',  '-f',  '-'] as String[]))
    assertEquals('''
 something      Do something.
 somethingElse  Do something else.

Default target is something.

''', output)
  }
  void testDefaultSomethingSetDefaultStringUsingP() {
    script = '''
target(something: "Do something.") { }
target(somethingElse: "Do something else.") { }
setDefaultTarget('something')
'''
    assertEquals(0, gant.processArgs(['-p',  '-f',  '-'] as String[]))
    assertEquals('''
 something      Do something.
 somethingElse  Do something else.

Default target is something.

''', output)
  }
  void testDefaultSomethingSetDefaultFailUsingP() {
    script = '''
target(something: "Do something.") { }
target(somethingElse: "Do something else.") { }
setDefaultTarget('fail')
'''
    assertEquals(0, gant.processArgs(['-p',  '-f',  '-'] as String[]))
    assertEquals('''
 something      Do something.
 somethingElse  Do something else.

''', output)
  }

  // -------------------------------------------------------------------------------------------------

  void testSomethingUsingT() {
    script = coreScript
    assertEquals(0, gant.processArgs(['-T',  '-f',  '-'] as String[]))
    assertEquals('''
 something      Do something.
 somethingElse  Do something else.

''', output)
  }
  void testSomethingAndCleanUsingT() {
    script = 'includeTargets << gant.targets.Clean\n' + coreScript
    assertEquals(0, gant.processArgs(['-T',  '-f',  '-'] as String[]))
    assertEquals('''
 clean          Action the cleaning.
 clobber        Action the clobbering. Do the cleaning first.
 something      Do something.
 somethingElse  Do something else.

''', output)
  }
  void testGStringsUsingT() {
    script = '''
def theWord = 'The Word'
target(something: "Do ${theWord}.") { }
target(somethingElse: "Do ${theWord}.") { }
'''
    assertEquals(0, gant.processArgs(['-T',  '-f',  '-'] as String[]))
    assertEquals('''
 something      Do The Word.
 somethingElse  Do The Word.

''', output)
  }
  void testDefaultSomethingUsingT() {
    script = '''
target(something: "Do something.") { }
target(somethingElse: "Do something else.") { }
target('default': "The default target.") { something() }
'''
    assertEquals(0, gant.processArgs(['-T',  '-f',  '-'] as String[]))
    assertEquals('''
 default        The default target.
 something      Do something.
 somethingElse  Do something else.

Default target is default.

''', output)
  }
  void testDefaultSomethingSetDefaultClosureUsingT() {
    script = '''
target(something: "Do something.") { }
target(somethingElse: "Do something else.") { }
setDefaultTarget(something)
'''
    assertEquals(0, gant.processArgs(['-T',  '-f',  '-'] as String[]))
    assertEquals('''
 something      Do something.
 somethingElse  Do something else.

Default target is something.

''', output)
  }
  void testDefaultSomethingSetDefaultStringUsingT() {
    script = '''
target(something: "Do something.") { }
target(somethingElse: "Do something else.") { }
setDefaultTarget('something')
'''
    assertEquals(0, gant.processArgs(['-T',  '-f',  '-'] as String[]))
    assertEquals('''
 something      Do something.
 somethingElse  Do something else.

Default target is something.

''', output)
  }
  void testDefaultSomethingSetDefaultFailUsingT() {
    script = '''
target(something: "Do something.") { }
target(somethingElse: "Do something else.") { }
setDefaultTarget('fail')
'''
    assertEquals(0, gant.processArgs(['-T',  '-f',  '-'] as String[]))
    assertEquals('''
 something      Do something.
 somethingElse  Do something else.

''', output)
  }

  // -------------------------------------------------------------------------------------------------

  /*
   *  In Gant 1.5.0 changes were made to the way the parameter to target was handled -- cf. GANT-56.
   *  However, it seems no tests were introduced for printing things out.  GANT-71 raised this point.  The
   *  following tests are directly the ones from GANT-71 by Jason Messmer -- possibly more tests needs
   *  adding.  In the end, GANT-71 was "Not A Bug", the original failing case was erroneous, and the new
   *  format worked fine -- despite not having any unit tests.
   */

  void test_GANT_71_oldFormatStillWorksUsingP() {
    script = '''
target(test: 'testing') { println("$it.name:") }
'''
    assertEquals(0, gant.processArgs(['-p', '-f', '-'] as String[]))
    assertEquals('''
 test  testing

''', output)
  }
  void test_GANT_71_newFormatWorksUsingP() {
    script = '''
target(name: 'test', description: 'testing') { println("$it.name:") }
'''
    assertEquals(0, gant.processArgs(['-p', '-f', '-'] as String[]))
    assertEquals('''
 test  testing

''', output)
  }

  void test_GANT_71_oldFormatStillWorksUsingT() {
    script = '''
target(test: 'testing') { println("$it.name:") }
'''
    assertEquals(0, gant.processArgs(['-T', '-f', '-'] as String[]))
    assertEquals('''
 test  testing

''', output)
  }
  void test_GANT_71_newFormatWorksUsingT() {
    script = '''
target(name: 'test', description: 'testing') { println("$it.name:") }
'''
    assertEquals(0, gant.processArgs(['-T', '-f', '-'] as String[]))
    assertEquals('''
 test  testing

''', output)
  }
}
