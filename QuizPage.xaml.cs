

namespace QuizMauiApp;

[QueryProperty(nameof(Category), "category")]
public partial class QuizPage : ContentPage
{
    // ─── Fields ────────────────────────────────────────────
    List<QuizQuestion> questions = new List<QuizQuestion>();
    int currentQuestionIndex = 0;
    int score = 0;
    string category = "";

    // ─── Shell navigation property ─────────────────────────
    public string Category
    {
        get => category;
        set
        {
            category = Uri.UnescapeDataString(value ?? "");
            InitializeQuestions(category);
            LoadQuestion();
        }
    }

    // ─── Constructor ───────────────────────────────────────
    public QuizPage()
    {
        InitializeComponent();
        // Note: questions are loaded via Category property setter
        // If you test QuizPage directly (no category), add a fallback:
        // InitializeQuestions("Culture générale");
        // LoadQuestion();
    }

    // ─── Step 8: Populate questions per category ──────────
    void InitializeQuestions(string selectedCategory)
    {
        // Reset state each time a new quiz starts
        currentQuestionIndex = 0;
        score = 0;

        switch (selectedCategory)
        {
            case "Culture générale":
                questions = new List<QuizQuestion>
                {
                    new QuizQuestion
                    {
                        QuestionText = "Quelle est la capitale de la France ?",
                        Answers = new List<string> { "Lyon", "Paris", "Marseille", "Nice" },
                        CorrectAnswer = "Paris"
                    },
                    new QuizQuestion
                    {
                        QuestionText = "Quel est le plus grand océan du monde ?",
                        Answers = new List<string> { "Atlantique", "Indien", "Arctique", "Pacifique" },
                        CorrectAnswer = "Pacifique"
                    },
                    new QuizQuestion
                    {
                        QuestionText = "Combien de continents y a-t-il sur Terre ?",
                        Answers = new List<string> { "5", "6", "7", "8" },
                        CorrectAnswer = "7"
                    }
                };
                break;

            case "Mathématiques":
                questions = new List<QuizQuestion>
                {
                    new QuizQuestion
                    {
                        QuestionText = "Combien font 7 × 8 ?",
                        Answers = new List<string> { "54", "56", "58", "62" },
                        CorrectAnswer = "56"
                    },
                    new QuizQuestion
                    {
                        QuestionText = "Quelle est la racine carrée de 144 ?",
                        Answers = new List<string> { "10", "11", "12", "13" },
                        CorrectAnswer = "12"
                    },
                    new QuizQuestion
                    {
                        QuestionText = "Combien font 15 % de 200 ?",
                        Answers = new List<string> { "20", "25", "30", "35" },
                        CorrectAnswer = "30"
                    }
                };
                break;

            case "Informatique":
                questions = new List<QuizQuestion>
                {
                    new QuizQuestion
                    {
                        QuestionText = "Que signifie HTML ?",
                        Answers = new List<string>
                        {
                            "Hyper Text Markup Language",
                            "High Tech Modern Language",
                            "Home Tool Markup Language",
                            "Hyperlink Text Mode Language"
                        },
                        CorrectAnswer = "Hyper Text Markup Language"
                    },
                    new QuizQuestion
                    {
                        QuestionText = "Quel est le langage principal de .NET MAUI ?",
                        Answers = new List<string> { "Java", "Python", "C#", "Swift" },
                        CorrectAnswer = "C#"
                    },
                    new QuizQuestion
                    {
                        QuestionText = "Que signifie CPU ?",
                        Answers = new List<string>
                        {
                            "Central Processing Unit",
                            "Computer Personal Unit",
                            "Core Power Unit",
                            "Central Program Utility"
                        },
                        CorrectAnswer = "Central Processing Unit"
                    }
                };
                break;

            case "Langues":
                questions = new List<QuizQuestion>
                {
                    new QuizQuestion
                    {
                        QuestionText = "Comment dit-on 'bonjour' en espagnol ?",
                        Answers = new List<string> { "Ciao", "Hola", "Hello", "Hallo" },
                        CorrectAnswer = "Hola"
                    },
                    new QuizQuestion
                    {
                        QuestionText = "Comment dit-on 'merci' en japonais ?",
                        Answers = new List<string> { "Konnichiwa", "Sayonara", "Arigato", "Suki" },
                        CorrectAnswer = "Arigato"
                    },
                    new QuizQuestion
                    {
                        QuestionText = "Comment dit-on 'eau' en anglais ?",
                        Answers = new List<string> { "Fire", "Earth", "Water", "Wind" },
                        CorrectAnswer = "Water"
                    }
                };
                break;

            default:
                // Fallback: a minimal default so the app never crashes
                questions = new List<QuizQuestion>
                {
                    new QuizQuestion
                    {
                        QuestionText = "Quelle est la capitale de l'Espagne ?",
                        Answers = new List<string> { "Lisbonne", "Madrid", "Barcelone", "Séville" },
                        CorrectAnswer = "Madrid"
                    }
                };
                break;
        }
    }

    // ─── Step 8 + Step 12: Single method to refresh UI ───
    void LoadQuestion()
    {
        ResetButtonColors(); // always reset first (Step 12)

        var question = questions[currentQuestionIndex];

        // Update question counter label
        QuestionCounterLabel.Text = $"Question {currentQuestionIndex + 1} / {questions.Count}";

        // Update question text
        QuestionLabel.Text = question.QuestionText;

        // Update all 4 answer buttons
        AnswerButton1.Text = question.Answers[0];
        AnswerButton2.Text = question.Answers[1];
        AnswerButton3.Text = question.Answers[2];
        AnswerButton4.Text = question.Answers[3];
    }

    // ─── Step 8 + 9 + 10 + 12: Answer click handler ──────
    async void OnAnswerClicked(object sender, EventArgs e)
    {
        if (sender is not Button button) return;

        var currentQuestion = questions[currentQuestionIndex];

        // ── Step 9: Score logic ──
        if (button.Text == currentQuestion.CorrectAnswer)
        {
            score++;
            // ── Step 12: Correct = green ──
            button.BackgroundColor = Colors.Green;
        }
        else
        {
            // ── Step 12: Wrong = red, show correct in green ──
            button.BackgroundColor = Colors.Red;

            // Find and highlight the correct answer button
            foreach (var btn in new[] { AnswerButton1, AnswerButton2, AnswerButton3, AnswerButton4 })
            {
                if (btn.Text == currentQuestion.CorrectAnswer)
                {
                    btn.BackgroundColor = Colors.Green;
                    break;
                }
            }
        }

        // ── Step 12: Wait 1 second so user sees the colors ──
        await Task.Delay(1000);

        // ── Step 8: Advance to next question ──
        currentQuestionIndex++;

        if (currentQuestionIndex < questions.Count)
        {
            // More questions remain
            LoadQuestion();
        }
        else
        {
            // ── Step 10: Navigate to ResultPage with score ──
            await Shell.Current.GoToAsync(
                $"{nameof(ResultPage)}?score={score}&totalQuestions={questions.Count}"
            );
        }
    }

    // ─── Step 12: Reset all button colors to default ─────
    void ResetButtonColors()
    {
        AnswerButton1.BackgroundColor = Colors.Gray;
        AnswerButton2.BackgroundColor = Colors.Gray;
        AnswerButton3.BackgroundColor = Colors.Gray;
        AnswerButton4.BackgroundColor = Colors.Gray;
    }
}