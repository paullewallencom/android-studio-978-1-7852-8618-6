package packt.com.getitright.repositories;

import packt.com.getitright.models.Question;
import packt.com.getitright.models.Quiz;

public class QuizRepository {

    public Quiz getQuiz(){

        Quiz quiz = new Quiz();
        Question q1 = quiz.addQuestion("1. What is the largest city in the world?",
            "http://cdn.acidcow.com/pics/20100923/skylines_of_large_cities_05.jpg" ,
            "tokyo");

        q1.addAnswer("delhi" , "Delhi, India");
        q1.addAnswer("tokyo" , "Tokyo, Japan");
        q1.addAnswer("saopaulo" , "Sao Paulo, Brazil");
        q1.addAnswer("nyc" , "New York, USA");


        Question q2 = quiz.addQuestion("2. What is the largest animal in the world?",
                "http://www.onekind.org/uploads/a-z/az_aardvark.jpg" ,
                "blue_whale");

        q2.addAnswer("african_elephant" , "African Elephant");
        q2.addAnswer("brown_bear" , "Brown Bear");
        q2.addAnswer("giraffe" , "Giraffe");
        q2.addAnswer("blue_whale" , "Blue whale");


        Question q3 = quiz.addQuestion("3. What is the highest mountain in the world?",
                "http://images.summitpost.org/medium/815426.jpg" ,
                "mount_everest");

        q3.addAnswer("mont_blanc" , "Mont Blanc");
        q3.addAnswer("pico_bolivar" , "Pico Bolívar");
        q3.addAnswer("mount_everest" , "Mount Everest");
        q3.addAnswer("kilimanjaro" , "Mount Kilimanjaro");

        return quiz;
    }
}
