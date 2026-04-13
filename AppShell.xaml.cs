
namespace QuizMauiApp;

public partial class AppShell : Shell
{
    public AppShell()
    {
        InitializeComponent();

        // ── Register all navigable pages ──────────────────
        Routing.RegisterRoute(nameof(CategoriesPage), typeof(CategoriesPage));
        Routing.RegisterRoute(nameof(QuizPage), typeof(QuizPage));
        Routing.RegisterRoute(nameof(ResultPage), typeof(ResultPage));
    }

   
}