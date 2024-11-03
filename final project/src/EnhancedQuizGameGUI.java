import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class EnhancedQuizGameGUI extends JFrame {
    private List<Question> mathQuestions;
    private List<Question> scienceQuestions;
    private List<Question> historyQuestions;
    private List<Question> programmingQuestions;
    private List<Question> currentQuestions;
    private int currentQuestionIndex;
    private int player1Score;
    private int player2Score;
    private boolean isTwoPlayerMode;

    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JPanel menuPanel;
    private JPanel categoryPanel;
    private JPanel gamePanel;
    private JPanel questionPanel;
    private JLabel questionLabel;
    private JButton[] answerButtons;
    private JLabel timerLabel;
    private JLabel scoreLabel;
    private Timer timer;
    private int timeLeft;

    // Custom colors
    private final Color BACKGROUND_COLOR = new Color(240, 248, 255); // Alice Blue
    private final Color PRIMARY_COLOR = new Color(70, 130, 180);    // Steel Blue
    private final Color SECONDARY_COLOR = new Color(255, 165, 0);   // Orange
    private final Color TEXT_COLOR = new Color(47, 79, 79);         // Dark Slate Gray

    // Custom font
    private Font customFont;

    public EnhancedQuizGameGUI() {
        initializeQuestions();

        setTitle("Ultimate Quiz Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Load custom font
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Roboto/Roboto-Black.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
            customFont = new Font("Arial", Font.PLAIN, 16);
        }

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(BACKGROUND_COLOR);

        createMenuPanel();
        createCategoryPanel();
        createGamePanel();

        cardPanel.add(menuPanel, "menu");
        cardPanel.add(categoryPanel, "category");
        cardPanel.add(gamePanel, "game");

        add(cardPanel);

        timer = new Timer(1000, e -> updateTimer());
    }

    private void initializeQuestions() {
        mathQuestions = new ArrayList<>();
        scienceQuestions = new ArrayList<>();
        historyQuestions = new ArrayList<>();
        programmingQuestions = new ArrayList<>();

        // Math questions
        mathQuestions.add(new MultipleChoiceQuestion(
            "What is 7 x 8?",
            new String[]{"54", "56", "62", "64"},
            1,
            ""
        ));
        mathQuestions.add(new MultipleChoiceQuestion(
            "What is the square root of 144?",
            new String[]{"10", "12", "14", "16"},
            1,
            ""
        ));
        mathQuestions.add(new MultipleChoiceQuestion(
            "What is 15% of 80?",
            new String[]{"10", "12", "15", "18"},
            1,
            ""
        ));
        mathQuestions.add(new MultipleChoiceQuestion(
            "What is the value of π (pi) to two decimal places?",
            new String[]{"3.14", "3.16", "3.18", "3.20"},
            0,
            ""
        ));
        mathQuestions.add(new MultipleChoiceQuestion(
            "What is the next number in the sequence: 2, 4, 8, 16, ...?",
            new String[]{"24", "28", "32", "36"},
            2,
            ""
        ));
        mathQuestions.add(new MultipleChoiceQuestion(
            "What is the area of a rectangle with length 8 cm and width 5 cm?",
            new String[]{"35 cm²", "38 cm²", "40 cm²", "42 cm²"},
            2,
            ""
        ));
        mathQuestions.add(new MultipleChoiceQuestion(
            "What is the result of 5² + 3³?",
            new String[]{"34", "36", "38", "40"},
            2,
            ""
        ));
        mathQuestions.add(new MultipleChoiceQuestion(
            "If x + 5 = 12, what is the value of x?",
            new String[]{"5", "6", "7", "8"},
            2,
            ""
        ));
        mathQuestions.add(new MultipleChoiceQuestion(
            "What is the sum of the angles in a triangle?",
            new String[]{"90°", "120°", "180°", "360°"},
            2,
            ""
        ));
        mathQuestions.add(new MultipleChoiceQuestion(
            "What is 3/4 expressed as a decimal?",
            new String[]{"0.65", "0.70", "0.75", "0.80"},
            2,
            ""
        ));

        // Science questions
        scienceQuestions.add(new MultipleChoiceQuestion(
            "Which planet is known as the Red Planet?",
            new String[]{"Venus", "Mars", "Jupiter", "Saturn"},
            1,
            "/images/mars.jpg"
        ));
        scienceQuestions.add(new MultipleChoiceQuestion(
            "What is the chemical symbol for water?",
            new String[]{"H2O", "CO2", "NaCl", "O2"},
            0,
            "/images/science.jpg"
        ));
        scienceQuestions.add(new MultipleChoiceQuestion(
            "What is the largest organ in the human body?",
            new String[]{"Heart", "Brain", "Liver", "Skin"},
            3,
            "/images/human_body.jpg"
        ));
        scienceQuestions.add(new MultipleChoiceQuestion(
            "Which of these is not a state of matter?",
            new String[]{"Solid", "Liquid", "Gas", "Energy"},
            3,
            "/images/matter_states.jpg"
        ));
        scienceQuestions.add(new MultipleChoiceQuestion(
            "What is the closest star to Earth?",
            new String[]{"Proxima Centauri", "Alpha Centauri", "Sirius", "The Sun"},
            3,
            "/images/stars.png"
        ));
        scienceQuestions.add(new MultipleChoiceQuestion(
            "Which of these is not a type of rock?",
            new String[]{"Igneous", "Sedimentary", "Metamorphic", "Volcanic"},
            3,
            "/images/rocks.jpg"
        ));
        scienceQuestions.add(new MultipleChoiceQuestion(
            "What is the speed of light in vacuum?",
            new String[]{"299,792 km/s", "300,000 km/s", "310,000 km/s", "320,000 km/s"},
            1,
            "/images/light_speed.jpg"
        ));
        scienceQuestions.add(new MultipleChoiceQuestion(
            "Which element has the chemical symbol 'Fe'?",
            new String[]{"Fluorine", "Ferrum (Iron)", "Francium", "Fermium"},
            1,
            "/images/periodic_table.png"
        ));
        scienceQuestions.add(new MultipleChoiceQuestion(
            "What is the process by which plants make their own food?",
            new String[]{"Photosynthesis", "Respiration", "Transpiration", "Germination"},
            0,
            "/images/photosynthesis.jpg"
        ));
        scienceQuestions.add(new MultipleChoiceQuestion(
            "Which of these is not a greenhouse gas?",
            new String[]{"Carbon dioxide", "Methane", "Water vapor", "Nitrogen"},
            3,
            "/images/greenhouse_gases.jpg"
        ));

        // History questions
        historyQuestions.add(new MultipleChoiceQuestion(
            "In which year did World War II end?",
            new String[]{"1943", "1945", "1947", "1950"},
            1,
            "/images/history.jpg"
        ));
        historyQuestions.add(new MultipleChoiceQuestion(
            "Who was the first President of the United States?",
            new String[]{"Thomas Jefferson", "John Adams", "George Washington", "Benjamin Franklin"},
            2,
            "/images/history2.jpg"
        ));
        historyQuestions.add(new MultipleChoiceQuestion(
            "In which year did Christopher Columbus first reach the Americas?",
            new String[]{"1492", "1500", "1510", "1520"},
            0,
            "/images/columbus.jpg"
        ));
        historyQuestions.add(new MultipleChoiceQuestion(
            "Who was the first woman to fly solo across the Atlantic Ocean?",
            new String[]{"Amelia Earhart", "Bessie Coleman", "Harriet Quimby", "Jacqueline Cochran"},
            0,
            "/images/aviation.jpg"
        ));
        historyQuestions.add(new MultipleChoiceQuestion(
            "Which ancient wonder was located in Alexandria, Egypt?",
            new String[]{"Hanging Gardens", "Colossus of Rhodes", "Lighthouse", "Temple of Artemis"},
            2,
            "/images/ancient_wonders.jpg"
        ));
        historyQuestions.add(new MultipleChoiceQuestion(
            "Who wrote the Declaration of Independence?",
            new String[]{"George Washington", "Benjamin Franklin", "John Adams", "Thomas Jefferson"},
            3,
            "/images/declaration.jpg"
        ));
        historyQuestions.add(new MultipleChoiceQuestion(
            "In which year did the Berlin Wall fall?",
            new String[]{"1987", "1989", "1991", "1993"},
            1,
            "/images/berlin_wall.jpg"
        ));
        historyQuestions.add(new MultipleChoiceQuestion(
            "Who was the first Emperor of Rome?",
            new String[]{"Julius Caesar", "Augustus", "Nero", "Caligula"},
            1,
            "/images/roman_empire.jpg"
        ));
        historyQuestions.add(new MultipleChoiceQuestion(
            "Which country was NOT part of the Allied Powers during World War II?",
            new String[]{"United States", "Soviet Union", "United Kingdom", "Italy"},
            3,
            "/images/allied_powers.png"
        ));
        historyQuestions.add(new MultipleChoiceQuestion(
            "In which year did the Titanic sink?",
            new String[]{"1910", "1912", "1914", "1916"},
            1,
            "/images/titanic.jpg"
        ));

        // Programming questions
        programmingQuestions.add(new MultipleChoiceQuestion(
            "Which of the following is not a programming language?",
            new String[]{"Java", "Python", "HTML", "C++"},
            2,
            "/images/programming.jpg"
        ));
        programmingQuestions.add(new MultipleChoiceQuestion(
            "What does CPU stand for?",
            new String[]{"Central Processing Unit", "Computer Personal Unit", "Central Processor Unifier", "Central Program Utility"},
            0,
            "/images/programming2.jpg"
        ));
        programmingQuestions.add(new MultipleChoiceQuestion(
            "Which data structure uses LIFO (Last In, First Out)?",
            new String[]{"Queue", "Stack", "Linked List", "Array"},
            1,
            "/images/programming3.png"
        ));
        programmingQuestions.add(new MultipleChoiceQuestion(
            "What does SQL stand for?",
            new String[]{"Structured Query Language", "Simple Question Language", "Structured Question Logic", "System Query Language"},
            0,
            "/images/programming4.png"
        ));
        programmingQuestions.add(new MultipleChoiceQuestion(
            "Which of these is not an object-oriented programming language?",
            new String[]{"Java", "C++", "Python", "C"},
            3,
            "/images/programming.jpg"
        ));
        programmingQuestions.add(new MultipleChoiceQuestion(
            "What is the correct file extension for Python files?",
            new String[]{".py", ".pt", ".pyt", ".pth"},
            0,
            "/images/programming5.png"
        ));
        programmingQuestions.add(new MultipleChoiceQuestion(
            "Which of these is not a type of loop in programming?",
            new String[]{"For", "While", "Do-While", "Repeat-Until"},
            3,
            "/images/programming6.png"
        ));
        programmingQuestions.add(new MultipleChoiceQuestion(
            "What does IDE stand for in programming?",
            new String[]{"Integrated Development Environment", "Interface Design Engine", "Integrated Debugging Environment", "Interactive Development Engine"},
            0,
            "/images/programming7.jpg"
        ));
        programmingQuestions.add(new MultipleChoiceQuestion(
            "Which of these is not a common sorting algorithm?",
            new String[]{"Bubble Sort", "Quick Sort", "Merge Sort", "Linear Sort"},
            3,
            "/images/programming8.png"
        ));
        programmingQuestions.add(new MultipleChoiceQuestion(
            "What is the purpose of the 'git' version control system?",
            new String[]{"To compile code", "To debug programs", "To track changes in source code", "To design user interfaces"},
            2,
            "/images/programming9.jpg"
        ));
    }

    private void createMenuPanel() {
        menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, BACKGROUND_COLOR, 0, h, PRIMARY_COLOR);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
                g2d.dispose();
            }
        };
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        JLabel titleLabel = new JLabel("Ultimate Quiz Game");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(customFont.deriveFont(Font.BOLD, 36));
        titleLabel.setForeground(TEXT_COLOR);

        JButton singlePlayerButton = createStyledButton("Single Player", "/icons/single_player.png");
        JButton twoPlayerButton = createStyledButton("Two Player", "/icons/two_player.png");
        JButton exitButton = createStyledButton("Exit", "/icons/exit.png");

        singlePlayerButton.addActionListener(e -> showCategorySelection(false));
        twoPlayerButton.addActionListener(e -> showCategorySelection(true));
        exitButton.addActionListener(e -> System.exit(0));

        menuPanel.add(titleLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        menuPanel.add(singlePlayerButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        menuPanel.add(twoPlayerButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        menuPanel.add(exitButton);
    }

    private void createCategoryPanel() {
        categoryPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, BACKGROUND_COLOR, 0, h, PRIMARY_COLOR);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
                g2d.dispose();
            }
        };
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        JLabel titleLabel = new JLabel("Select a Category");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(customFont.deriveFont(Font.BOLD, 36));
        titleLabel.setForeground(TEXT_COLOR);

        JButton mathButton = createStyledButton("Math", "/icons/iconmath.png");
        JButton scienceButton = createStyledButton("Science", "/icons/iconscience.png");
        JButton historyButton = createStyledButton("History", "/icons/iconhistory.jpg");
        JButton programmingButton = createStyledButton("Programming", "/icons/iconprogramming.png");

        mathButton.addActionListener(e -> startGame(mathQuestions));
        scienceButton.addActionListener(e -> startGame(scienceQuestions));
        historyButton.addActionListener(e -> startGame(historyQuestions));
        programmingButton.addActionListener(e -> startGame(programmingQuestions));

        categoryPanel.add(titleLabel);
        categoryPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        categoryPanel.add(mathButton);
        categoryPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        categoryPanel.add(scienceButton);
        categoryPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        categoryPanel.add(historyButton);
        categoryPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        categoryPanel.add(programmingButton);
    }

    private void createGamePanel() {
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBackground(BACKGROUND_COLOR);

        questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        questionPanel.setBackground(BACKGROUND_COLOR);

        questionLabel = new JLabel();
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionLabel.setFont(customFont.deriveFont(Font.BOLD, 20));
        questionLabel.setForeground(TEXT_COLOR);

        answerButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = createStyledButton("", null);
            final int index = i;
            answerButtons[i].addActionListener(e -> checkAnswer(index));
        }

        questionPanel.add(questionLabel);
        questionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        for (JButton button : answerButtons) {
            questionPanel.add(button);
            questionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        gamePanel.add(questionPanel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(PRIMARY_COLOR);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        timerLabel = new JLabel("Time: 15");
        timerLabel.setFont(customFont.deriveFont(Font.PLAIN, 18));
        timerLabel.setForeground(Color.WHITE);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(customFont.deriveFont(Font.PLAIN, 18));
        scoreLabel.setForeground(Color.WHITE);

        infoPanel.add(timerLabel, BorderLayout.WEST);
        infoPanel.add(scoreLabel, BorderLayout.EAST);
        gamePanel.add(infoPanel, BorderLayout.NORTH);
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(customFont.deriveFont(Font.PLAIN, 18));
        button.setForeground(TEXT_COLOR);
        button.setBackground(SECONDARY_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        if (iconPath != null) {
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
                Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(img));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(SECONDARY_COLOR.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(SECONDARY_COLOR);
            }
        });

        return button;
    }

    private void showCategorySelection(boolean twoPlayerMode) {
        isTwoPlayerMode = twoPlayerMode;
        cardLayout.show(cardPanel, "category");
    }

    private void startGame(List<Question> questions) {
        currentQuestions = questions;
        player1Score = 0;
        player2Score = 0;
        currentQuestionIndex = 0;
        updateScoreLabel();
        showNextQuestion();
        cardLayout.show(cardPanel, "game");
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < currentQuestions.size()) {
            questionPanel.removeAll();
            questionPanel.add(questionLabel);
            questionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            for (JButton button : answerButtons) {
                questionPanel.add(button);
                questionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
    
            Question question = currentQuestions.get(currentQuestionIndex);
            questionLabel.setText("<html><body style='width: 400px;'>" + question.getQuestionText() + "</body></html>");
    
            if (question.getImagePath() != null) {
                try {
                    ImageIcon icon = new ImageIcon(getClass().getResource(question.getImagePath()));
                    Image img = icon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
                    JLabel imageLabel = new JLabel(new ImageIcon(img));
                    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    questionPanel.add(imageLabel, 1);
                    questionPanel.add(Box.createRigidArea(new Dimension(0, 20)), 2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    
            if (question instanceof MultipleChoiceQuestion) {
                MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;
                String[] options = mcq.getOptions();
                for (int i = 0; i < options.length; i++) {
                    answerButtons[i].setText(options[i]);
                    answerButtons[i].setVisible(true);
                }
                for (int i = options.length; i < 4; i++) {
                    answerButtons[i].setVisible(false);
                }
            } else if (question instanceof TrueFalseQuestion) {
                answerButtons[0].setText("True");
                answerButtons[1].setText("False");
                
                answerButtons[0].setVisible(true);
                answerButtons[1].setVisible(true);
                answerButtons[2].setVisible(false);
                answerButtons[3].setVisible(false);
            }
    
            timeLeft = 15;
            updateTimerLabel();
            timer.start();
    
            questionPanel.setVisible(false);
            Timer animationTimer = new Timer(50, new ActionListener() {
                float alpha = 0f;
                @Override
                public void actionPerformed(ActionEvent e) {
                    alpha += 0.1f;
                    if (alpha > 1f) {
                        alpha = 1f;
                        ((Timer)e.getSource()).stop();
                    }
                    questionPanel.setVisible(true);
                    questionPanel.setOpaque(false);
                    questionPanel.setBackground(new Color(BACKGROUND_COLOR.getRed(), BACKGROUND_COLOR.getGreen(), BACKGROUND_COLOR.getBlue(), (int)(alpha * 255)));
                    questionPanel.repaint();
                }
            });
            animationTimer.start();
        } else {
            endGame();
        }
    }

    private void checkAnswer(int selectedIndex) {
        timer.stop();
        Question question = currentQuestions.get(currentQuestionIndex);
        boolean correct = false;

        if (question instanceof MultipleChoiceQuestion) {
            correct = ((MultipleChoiceQuestion) question).isCorrect(selectedIndex);
        } else if (question instanceof TrueFalseQuestion) {
            correct = ((TrueFalseQuestion) question).isCorrect(selectedIndex == 0);
        }

        if (correct) {
            if (isTwoPlayerMode) {
                if (currentQuestionIndex % 2 == 0) {
                    player1Score++;
                } else {
                    player2Score++;
                }
            } else {
                player1Score++;
            }
            updateScoreLabel();
            showFeedback("Correct!", Color.GREEN);
        } else {
            showFeedback("Incorrect!", Color.RED);
        }

        currentQuestionIndex++;
        Timer transitionTimer = new Timer(1500, e -> showNextQuestion());
        transitionTimer.setRepeats(false);
        transitionTimer.start();
    }

    private void showFeedback(String message, Color color) {
        JLabel feedbackLabel = new JLabel(message);
        feedbackLabel.setFont(customFont.deriveFont(Font.BOLD, 24));
        feedbackLabel.setForeground(color);
        feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        questionPanel.add(feedbackLabel, 0);
        questionPanel.revalidate();
        questionPanel.repaint();

        Timer removeTimer = new Timer(1500, e -> {
            questionPanel.remove(feedbackLabel);
            questionPanel.revalidate();
            questionPanel.repaint();
        });
        removeTimer.setRepeats(false);
        removeTimer.start();
    }

    private void updateTimer() {
        timeLeft--;
        updateTimerLabel();
        if (timeLeft == 0) {
            timer.stop();
            showFeedback("Time's up!", Color.ORANGE);
            currentQuestionIndex++;
            Timer transitionTimer = new Timer(1500, e -> showNextQuestion());
            transitionTimer.setRepeats(false);
            transitionTimer.start();
        }
    }

    private void updateTimerLabel() {
        timerLabel.setText("Time: " + timeLeft);
    }

    private void updateScoreLabel() {
        if (isTwoPlayerMode) {
            scoreLabel.setText("P1: " + player1Score + " | P2: " + player2Score);
        } else {
            scoreLabel.setText("Score: " + player1Score);
        }
    }

    private void endGame() {
        timer.stop();
        String message;
        if (isTwoPlayerMode) {
            if (player1Score > player2Score) {
                message = "Player 1 wins!\nPlayer 1: " + player1Score + "\nPlayer 2: " + player2Score;
            } else if (player2Score > player1Score) {
                message = "Player 2 wins!\nPlayer 1: " + player1Score + "\nPlayer 2: " + player2Score;
            } else {
                message = "It's a tie!\nPlayer 1: " + player1Score + "\nPlayer 2: " + player2Score;
            }
        } else {
            message = "Game Over!\nYour score: " + player1Score + "/" + currentQuestions.size();
        }
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        cardLayout.show(cardPanel, "menu");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EnhancedQuizGameGUI game = new EnhancedQuizGameGUI();
            game.setVisible(true);
        });
    }
}

class Question {
    protected String questionText;
    protected String imagePath;

    public Question(String questionText, String imagePath) {
        this.questionText = questionText;
        this.imagePath = imagePath;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getImagePath() {
        return imagePath;
    }
}

class MultipleChoiceQuestion extends Question {
    private String[] options;
    private int correctAnswer;

    public MultipleChoiceQuestion(String questionText, String[] options, int correctAnswer, String imagePath) {
        super(questionText, imagePath);
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String[] getOptions() {
        return options;
    }

    public boolean isCorrect(int selectedAnswer) {
        return selectedAnswer == correctAnswer;
    }
}

class TrueFalseQuestion extends Question {
    private boolean correctAnswer;

    public TrueFalseQuestion(String questionText, boolean correctAnswer, String imagePath) {
        super(questionText, imagePath);
        this.correctAnswer = correctAnswer;
    }

    public boolean isCorrect(boolean selectedAnswer) {
        return selectedAnswer == correctAnswer;
    }
}