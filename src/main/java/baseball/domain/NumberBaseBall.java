package baseball.domain;

import java.util.List;

public class NumberBaseBall {
    public static final String STRIKE = "스트라이크";
    public static final String BALL = "볼";
    public static final String NOTHING = "낫싱";
    private final RandomNumberPicker randomNumberPicker;
    private final InputOutputHandler inputOutputHandler;
    private final Me me;

    public NumberBaseBall(
            RandomNumberPicker randomNumberPicker,
            InputOutputHandler inputOutputHandler,
            Me me) {
        this.randomNumberPicker = randomNumberPicker;
        this.inputOutputHandler = inputOutputHandler;
        this.me = me;
    }

    public void startGame(int numberLength) {
        // 랜덤한 서로다른 숫자 생성
        List<Integer> answer = randomNumberPicker.generateDistinctNumbers(numberLength);

        boolean isFinish = false;

        while (!isFinish) {
            // 정답 맞추기 시작
            inputOutputHandler.printText("숫자를 입력해주세요 : ");
            List<Integer> guessAnswer = me.guessAnswer(numberLength);

            //추측한 정답에 대한 결과
            Result guessResult = verifyGuess(answer, guessAnswer);
            inputOutputHandler.printlnText(guessResult.toString());

            //결과가 정답인지 확인
            if(guessResult.isCorrect()) isFinish = true;
        }

        inputOutputHandler.printlnText("3개의 숫자를 모두 맞히셨습니다! 게임 종료\n게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
        int inputNumber = inputOutputHandler.inputOneNumber();

        if(inputNumber == 1) startGame(numberLength);

        return;
    }

    public Result verifyGuess(List<Integer> answer, List<Integer> guessAnswer) {
        int strike = 0;
        int ball = 0;

        for(int i=0; i<guessAnswer.size(); i++) {
            int number = guessAnswer.get(i);
            int index = answer.indexOf(number);

            if(index == -1) continue;

            if(index == i) strike++;
            else ball++;
        }

        return new Result(ball,strike);
    }
}
