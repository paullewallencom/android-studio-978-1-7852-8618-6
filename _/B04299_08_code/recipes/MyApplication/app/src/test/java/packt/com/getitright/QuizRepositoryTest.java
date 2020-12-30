package packt.com.getitright;


import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import packt.com.getitright.activities.GooglePlayServicesActivity;
import packt.com.getitright.models.Question;
import packt.com.getitright.models.Quiz;
import packt.com.getitright.repositories.QuizRepository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)

public class QuizRepositoryTest {

    private QuizRepository mRepository;

    @Before
    public void setup() throws Exception {

        mRepository = new QuizRepository();
        assertNotNull("QuizRepository is not instantiated", mRepository);
    }

    @Test
    public void quizHasQuestions() throws Exception {
        Quiz quiz = mRepository.getQuiz();
        ArrayList<Question> questions = quiz.getQuestions();

        assertNotNull("quiz could not be created", quiz);
        assertNotNull("quiz contains no questions", questions);
        assertTrue("quiz contains no questions", questions.size()>0);
    }

    @Test
    public void quizHasSufficientQuestions() throws Exception {
        Quiz quiz = mRepository.getQuiz();
        ArrayList<Question> questions = quiz.getQuestions();
        assertNotNull("quiz could not be created", quiz);
        assertNotNull("quiz contains no questions", questions);
        assertTrue("quiz contains insufficient questions", questions.size()>=2);
    }
}