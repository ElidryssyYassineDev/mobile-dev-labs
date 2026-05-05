namespace MicrosoftMauiCourse
{
    public partial class MainPage : ContentPage
    {
       
        public MainPage()
        {
            InitializeComponent();
        }

        string? translatedNumber;
        private void OnTranslate(Object sender, EventArgs e)
        {
            string enteredNumber = PhoneNumberText.Text;
            translatedNumber = PhonewordTranslator.ToNumber(enteredNumber);
            if (!string.IsNullOrWhiteSpace(translatedNumber))
            {
                CallButton.IsEnabled = true;
                CallButton.Text = "Call " + translatedNumber;
            }
            else
            {
               CallButton.IsEnabled = false;
               CallButton.Text = "Call";
            }
        }
         async void OnCall(Object sender, EventArgs e)
        {
            if(await this.DisplayAlertAsync("Dial a Number", "Would you like to call " + translatedNumber + "?", "Yes", "No"))
            {
                try
                {
                    if (PhoneDialer.Default.IsSupported && !string.IsNullOrWhiteSpace(translatedNumber))
                        PhoneDialer.Default.Open(translatedNumber);
                }
                catch (ArgumentNullException)
                {
                    await DisplayAlertAsync("Unable to dial", "Phone number was not valid.", "OK");
                }
                catch (Exception)
                {
                    // Other error has occurred.
                    await DisplayAlertAsync("Unable to dial", "Phone dialing failed.", "OK");
                }
            }
        }

    }
}
