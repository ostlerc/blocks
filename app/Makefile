.PHONY : all setup gui test clean

BLOCKDIR=src/pt314/blocks
TESTDIR=test/pt314/blocks/game
GAMEDIR=$(BLOCKDIR)/game
GUIDIR=$(BLOCKDIR)/gui

GAMESRC= \
         $(GAMEDIR)/Block.java \
         $(GAMEDIR)/Direction.java \
         $(GAMEDIR)/GameBoard.java \
         $(GAMEDIR)/HorizontalBlock.java \
         $(GAMEDIR)/TargetBlock.java \
         $(GAMEDIR)/VerticalBlock.java

GUISRC= \
        $(GUIDIR)/GridButton.java \
        $(GUIDIR)/SimpleGUI.java

TESTSRC= \
         $(TESTDIR)/AllTests.java \
         $(TESTDIR)/HorizontalBlockTest.java \
         $(TESTDIR)/TargetBlockTest.java \
         $(TESTDIR)/VerticalBlockTest.java \
         $(TESTDIR)/GameBoardTest.java
GTESTSRC=$(BLOCKDIR)/GUITest.java

CLASSES = \
        $(GAMESRC) \
        $(GUISRC) \
        $(GTESTSRC)

JUNITCLASSES = \
             $(TESTSRC)

.SUFFIXES: .java .class

JUNIT=junit-4.12.jar
HAMCREST=hamcrest-core-1.3.jar

JARS=$(HAMCREST):$(JUNIT)

JFLAGS = -cp $(JARS):src:test
JC = javac

default: all

.java.class: $(JUNIT) $(HAMCREST)
	$(JC) $(JFLAGS) $*.java

classes: $(CLASSES:.java=.class)
junit: $(JUNITCLASSES:.java=.class)

# parallel compile support, first game classes then tests can be built
all: $(JUNIT) $(HAMCREST)
	$(MAKE) classes
	$(MAKE) junit

setup : $(JUNIT) $(HAMCREST)

gui : $(CLASSES:.java=.class)
	java -cp src pt314.blocks.GUITest

test : $(JUNIT) $(HAMCREST) $(CLASSES:.java=.class) $(JUNITCLASSES:.java=.class)
	java -cp test $(JFLAGS) org.junit.runner.JUnitCore pt314.blocks.game.AllTests

$(JUNIT):
	wget http://search.maven.org/remotecontent?filepath=junit/junit/4.12/junit-4.12.jar -O $(JUNIT)

$(HAMCREST):
	wget http://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar -O $(HAMCREST)

clean:
	$(RM) $(CLASSES:.java=.class) $(JUNITCLASSES:.java=.class)
	$(RM) $(JUNIT) $(HAMCREST)
