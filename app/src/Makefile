.PHONY : all setup gui test clean

BLOCKDIR=pt314/blocks
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

TESTSRC=$(BLOCKDIR)/Test.java
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

JFLAGS = -cp $(JARS).:
JC = javac

.java.class:
	$(JC) $(JFLAGS) $*.java

default: all

all: $(JUNIT) $(HAMCREST) $(CLASSES:.java=.class) $(JUNITCLASSES:.java=.class)

setup : $(JUNIT) $(HAMCREST)

gui : $(CLASSES:.java=.class)
	java pt314.blocks.GUITest

test : $(JUNIT) $(HAMCREST) $(CLASSES:.java=.class) $(JUNITCLASSES:.java=.class)
	java $(JFLAGS) pt314.blocks.Test

$(JUNIT):
	wget http://search.maven.org/remotecontent?filepath=junit/junit/4.12/junit-4.12.jar -O $(JUNIT)

$(HAMCREST):
	wget http://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar -O $(HAMCREST)

clean:
	$(RM) $(CLASSES:.java=.class) $(JUNITCLASSES:.java=.class)
	$(RM) $(JUNIT) $(HAMCREST)
