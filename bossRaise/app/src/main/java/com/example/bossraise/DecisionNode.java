package com.example.bossraise;

public class DecisionNode {

    int nodeID;
    int option1id;
    int option2id;
    int option3id;

    DecisionNode option1;
    DecisionNode option2;
    DecisionNode option3;

    DecisionNode linkedNode;

    String description;
    String question;

    String op1Description;
    String op2Description;
    String op3Description;


    public DecisionNode()  {
    }

    public DecisionNode getLinkedNode()  { return linkedNode; }
    public void setLinkedNode(DecisionNode linkedNode)  { this.linkedNode = linkedNode; }

    public int getNodeID() { return nodeID; }
    public void setNodeID(int nodeID) { this.nodeID = nodeID; }

    // get and set option decisions
    public String getOp1Description() { return op1Description; }
    public void setOp1Description(String op1Description) { this.op1Description = op1Description; }
    public String getOp2Description() { return op2Description; }
    public void setOp2Description(String op2Description) {  this.op2Description = op2Description; }
    public String getOp3Description() { return op3Description; }
    public void setOp3Description(String op3Description) { this.op3Description = op3Description; }

    // get and set description and query
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    // get and set options
    public DecisionNode getOption1() { return option1;  }
    public void setOption1(DecisionNode option1) { this.option1 = option1;  }
    public DecisionNode getOption2() {  return option2;  }
    public void setOption2(DecisionNode option2) { this.option2 = option2;  }
    public DecisionNode getOption3() { return option3; }
    public void setOption3(DecisionNode option3) { this.option3 = option3;  }

    // get and set id of options
    public int getOption1id() { return option1id;  }
    public void setOption1id(int option1id) { this.option1id = option1id;  }
    public int getOption2id() { return option2id;  }
    public void setOption2id(int option2id) { this.option2id = option2id;  }
    public int getOption3id() { return option3id;  }
    public void setOption3id(int option3id) { this.option3id = option3id;  }



}