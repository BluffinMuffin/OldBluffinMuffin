namespace BluffinPokerGui.Game
{
    partial class TableViewerForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(TableViewerForm));
            this.logConsole = new BluffinPokerGui.LogConsole();
            this.btnHelp = new VIBlend.WinForms.Controls.vButton();
            this.pnlTable = new System.Windows.Forms.Panel();
            this.lblPot9 = new System.Windows.Forms.Label();
            this.lblPot8 = new System.Windows.Forms.Label();
            this.lblPot7 = new System.Windows.Forms.Label();
            this.lblPot6 = new System.Windows.Forms.Label();
            this.lblPot5 = new System.Windows.Forms.Label();
            this.lblPot0 = new System.Windows.Forms.Label();
            this.lblPot1 = new System.Windows.Forms.Label();
            this.lblPot2 = new System.Windows.Forms.Label();
            this.lblPot3 = new System.Windows.Forms.Label();
            this.lblPot4 = new System.Windows.Forms.Label();
            this.lblPot9Title = new System.Windows.Forms.Label();
            this.lblPot8Title = new System.Windows.Forms.Label();
            this.lblPot7Title = new System.Windows.Forms.Label();
            this.lblPot6Title = new System.Windows.Forms.Label();
            this.lblPot5Title = new System.Windows.Forms.Label();
            this.lblPot4Title = new System.Windows.Forms.Label();
            this.lblPot3Title = new System.Windows.Forms.Label();
            this.lblPot2Title = new System.Windows.Forms.Label();
            this.lblPot1Title = new System.Windows.Forms.Label();
            this.lblPot0Title = new System.Windows.Forms.Label();
            this.label10 = new System.Windows.Forms.Label();
            this.label9 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.cardPictureBox5 = new BluffinPokerGui.Game.CardPictureBox();
            this.cardPictureBox4 = new BluffinPokerGui.Game.CardPictureBox();
            this.cardPictureBox3 = new BluffinPokerGui.Game.CardPictureBox();
            this.cardPictureBox2 = new BluffinPokerGui.Game.CardPictureBox();
            this.cardPictureBox1 = new BluffinPokerGui.Game.CardPictureBox();
            this.pokerPlayerHud10 = new BluffinPokerGui.Game.PokerPlayerHud();
            this.pokerPlayerHud9 = new BluffinPokerGui.Game.PokerPlayerHud();
            this.pokerPlayerHud8 = new BluffinPokerGui.Game.PokerPlayerHud();
            this.pokerPlayerHud7 = new BluffinPokerGui.Game.PokerPlayerHud();
            this.pokerPlayerHud6 = new BluffinPokerGui.Game.PokerPlayerHud();
            this.pokerPlayerHud5 = new BluffinPokerGui.Game.PokerPlayerHud();
            this.pokerPlayerHud4 = new BluffinPokerGui.Game.PokerPlayerHud();
            this.pokerPlayerHud3 = new BluffinPokerGui.Game.PokerPlayerHud();
            this.pokerPlayerHud2 = new BluffinPokerGui.Game.PokerPlayerHud();
            this.pokerPlayerHud1 = new BluffinPokerGui.Game.PokerPlayerHud();
            this.lblTotalPot = new System.Windows.Forms.Label();
            this.lblTotalPotTitle = new System.Windows.Forms.Label();
            this.pnlTable.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.cardPictureBox5)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.cardPictureBox4)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.cardPictureBox3)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.cardPictureBox2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.cardPictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // logConsole
            // 
            this.logConsole.Location = new System.Drawing.Point(0, 553);
            this.logConsole.Name = "logConsole";
            this.logConsole.Size = new System.Drawing.Size(1019, 135);
            this.logConsole.TabIndex = 3;
            // 
            // btnHelp
            // 
            this.btnHelp.AllowAnimations = true;
            this.btnHelp.BackColor = System.Drawing.Color.Transparent;
            this.btnHelp.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnHelp.Image = global::BluffinPokerGUI.Properties.Resources.help;
            this.btnHelp.Location = new System.Drawing.Point(880, 12);
            this.btnHelp.Name = "btnHelp";
            this.btnHelp.RoundedCornersMask = ((byte)(15));
            this.btnHelp.Size = new System.Drawing.Size(134, 23);
            this.btnHelp.TabIndex = 1;
            this.btnHelp.Text = "HELP";
            this.btnHelp.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.btnHelp.UseVisualStyleBackColor = true;
            this.btnHelp.VIBlendTheme = VIBlend.Utilities.VIBLEND_THEME.EXPRESSIONDARK;
            this.btnHelp.Click += new System.EventHandler(this.btnHelp_Click);
            // 
            // pnlTable
            // 
            this.pnlTable.BackgroundImage = global::BluffinPokerGUI.Properties.Resources.tableBackground;
            this.pnlTable.Controls.Add(this.lblPot9);
            this.pnlTable.Controls.Add(this.lblPot8);
            this.pnlTable.Controls.Add(this.lblPot7);
            this.pnlTable.Controls.Add(this.lblPot6);
            this.pnlTable.Controls.Add(this.lblPot5);
            this.pnlTable.Controls.Add(this.lblPot0);
            this.pnlTable.Controls.Add(this.lblPot1);
            this.pnlTable.Controls.Add(this.lblPot2);
            this.pnlTable.Controls.Add(this.lblPot3);
            this.pnlTable.Controls.Add(this.lblPot4);
            this.pnlTable.Controls.Add(this.lblPot9Title);
            this.pnlTable.Controls.Add(this.lblPot8Title);
            this.pnlTable.Controls.Add(this.lblPot7Title);
            this.pnlTable.Controls.Add(this.lblPot6Title);
            this.pnlTable.Controls.Add(this.lblPot5Title);
            this.pnlTable.Controls.Add(this.lblPot4Title);
            this.pnlTable.Controls.Add(this.lblPot3Title);
            this.pnlTable.Controls.Add(this.lblPot2Title);
            this.pnlTable.Controls.Add(this.lblPot1Title);
            this.pnlTable.Controls.Add(this.lblPot0Title);
            this.pnlTable.Controls.Add(this.label10);
            this.pnlTable.Controls.Add(this.label9);
            this.pnlTable.Controls.Add(this.label8);
            this.pnlTable.Controls.Add(this.label7);
            this.pnlTable.Controls.Add(this.label6);
            this.pnlTable.Controls.Add(this.label5);
            this.pnlTable.Controls.Add(this.label4);
            this.pnlTable.Controls.Add(this.label3);
            this.pnlTable.Controls.Add(this.label2);
            this.pnlTable.Controls.Add(this.label1);
            this.pnlTable.Controls.Add(this.cardPictureBox5);
            this.pnlTable.Controls.Add(this.cardPictureBox4);
            this.pnlTable.Controls.Add(this.cardPictureBox3);
            this.pnlTable.Controls.Add(this.cardPictureBox2);
            this.pnlTable.Controls.Add(this.cardPictureBox1);
            this.pnlTable.Controls.Add(this.pokerPlayerHud10);
            this.pnlTable.Controls.Add(this.pokerPlayerHud9);
            this.pnlTable.Controls.Add(this.pokerPlayerHud8);
            this.pnlTable.Controls.Add(this.pokerPlayerHud7);
            this.pnlTable.Controls.Add(this.pokerPlayerHud6);
            this.pnlTable.Controls.Add(this.pokerPlayerHud5);
            this.pnlTable.Controls.Add(this.pokerPlayerHud4);
            this.pnlTable.Controls.Add(this.pokerPlayerHud3);
            this.pnlTable.Controls.Add(this.pokerPlayerHud2);
            this.pnlTable.Controls.Add(this.pokerPlayerHud1);
            this.pnlTable.Controls.Add(this.lblTotalPot);
            this.pnlTable.Controls.Add(this.lblTotalPotTitle);
            this.pnlTable.Location = new System.Drawing.Point(0, 0);
            this.pnlTable.Name = "pnlTable";
            this.pnlTable.Size = new System.Drawing.Size(874, 556);
            this.pnlTable.TabIndex = 0;
            // 
            // lblPot9
            // 
            this.lblPot9.BackColor = System.Drawing.Color.Black;
            this.lblPot9.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot9.ForeColor = System.Drawing.Color.White;
            this.lblPot9.Location = new System.Drawing.Point(89, 503);
            this.lblPot9.Name = "lblPot9";
            this.lblPot9.Size = new System.Drawing.Size(100, 18);
            this.lblPot9.TabIndex = 47;
            this.lblPot9.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot9.Visible = false;
            // 
            // lblPot8
            // 
            this.lblPot8.BackColor = System.Drawing.Color.Black;
            this.lblPot8.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot8.ForeColor = System.Drawing.Color.White;
            this.lblPot8.Location = new System.Drawing.Point(89, 485);
            this.lblPot8.Name = "lblPot8";
            this.lblPot8.Size = new System.Drawing.Size(100, 18);
            this.lblPot8.TabIndex = 46;
            this.lblPot8.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot8.Visible = false;
            // 
            // lblPot7
            // 
            this.lblPot7.BackColor = System.Drawing.Color.Black;
            this.lblPot7.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot7.ForeColor = System.Drawing.Color.White;
            this.lblPot7.Location = new System.Drawing.Point(89, 467);
            this.lblPot7.Name = "lblPot7";
            this.lblPot7.Size = new System.Drawing.Size(100, 18);
            this.lblPot7.TabIndex = 45;
            this.lblPot7.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot7.Visible = false;
            // 
            // lblPot6
            // 
            this.lblPot6.BackColor = System.Drawing.Color.Black;
            this.lblPot6.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot6.ForeColor = System.Drawing.Color.White;
            this.lblPot6.Location = new System.Drawing.Point(89, 449);
            this.lblPot6.Name = "lblPot6";
            this.lblPot6.Size = new System.Drawing.Size(100, 18);
            this.lblPot6.TabIndex = 44;
            this.lblPot6.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot6.Visible = false;
            // 
            // lblPot5
            // 
            this.lblPot5.BackColor = System.Drawing.Color.Black;
            this.lblPot5.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot5.ForeColor = System.Drawing.Color.White;
            this.lblPot5.Location = new System.Drawing.Point(89, 431);
            this.lblPot5.Name = "lblPot5";
            this.lblPot5.Size = new System.Drawing.Size(100, 18);
            this.lblPot5.TabIndex = 43;
            this.lblPot5.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot5.Visible = false;
            // 
            // lblPot0
            // 
            this.lblPot0.BackColor = System.Drawing.Color.Black;
            this.lblPot0.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot0.ForeColor = System.Drawing.Color.White;
            this.lblPot0.Location = new System.Drawing.Point(89, 9);
            this.lblPot0.Name = "lblPot0";
            this.lblPot0.Size = new System.Drawing.Size(100, 18);
            this.lblPot0.TabIndex = 42;
            this.lblPot0.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot0.Visible = false;
            // 
            // lblPot1
            // 
            this.lblPot1.BackColor = System.Drawing.Color.Black;
            this.lblPot1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot1.ForeColor = System.Drawing.Color.White;
            this.lblPot1.Location = new System.Drawing.Point(89, 27);
            this.lblPot1.Name = "lblPot1";
            this.lblPot1.Size = new System.Drawing.Size(100, 18);
            this.lblPot1.TabIndex = 41;
            this.lblPot1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot1.Visible = false;
            // 
            // lblPot2
            // 
            this.lblPot2.BackColor = System.Drawing.Color.Black;
            this.lblPot2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot2.ForeColor = System.Drawing.Color.White;
            this.lblPot2.Location = new System.Drawing.Point(89, 45);
            this.lblPot2.Name = "lblPot2";
            this.lblPot2.Size = new System.Drawing.Size(100, 18);
            this.lblPot2.TabIndex = 40;
            this.lblPot2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot2.Visible = false;
            // 
            // lblPot3
            // 
            this.lblPot3.BackColor = System.Drawing.Color.Black;
            this.lblPot3.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot3.ForeColor = System.Drawing.Color.White;
            this.lblPot3.Location = new System.Drawing.Point(89, 63);
            this.lblPot3.Name = "lblPot3";
            this.lblPot3.Size = new System.Drawing.Size(100, 18);
            this.lblPot3.TabIndex = 39;
            this.lblPot3.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot3.Visible = false;
            // 
            // lblPot4
            // 
            this.lblPot4.BackColor = System.Drawing.Color.Black;
            this.lblPot4.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot4.ForeColor = System.Drawing.Color.White;
            this.lblPot4.Location = new System.Drawing.Point(89, 81);
            this.lblPot4.Name = "lblPot4";
            this.lblPot4.Size = new System.Drawing.Size(100, 18);
            this.lblPot4.TabIndex = 38;
            this.lblPot4.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot4.Visible = false;
            // 
            // lblPot9Title
            // 
            this.lblPot9Title.BackColor = System.Drawing.Color.Black;
            this.lblPot9Title.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot9Title.ForeColor = System.Drawing.Color.White;
            this.lblPot9Title.Location = new System.Drawing.Point(12, 503);
            this.lblPot9Title.Name = "lblPot9Title";
            this.lblPot9Title.Size = new System.Drawing.Size(71, 18);
            this.lblPot9Title.TabIndex = 37;
            this.lblPot9Title.Text = "Side Pot 9:";
            this.lblPot9Title.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot9Title.Visible = false;
            // 
            // lblPot8Title
            // 
            this.lblPot8Title.BackColor = System.Drawing.Color.Black;
            this.lblPot8Title.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot8Title.ForeColor = System.Drawing.Color.White;
            this.lblPot8Title.Location = new System.Drawing.Point(12, 485);
            this.lblPot8Title.Name = "lblPot8Title";
            this.lblPot8Title.Size = new System.Drawing.Size(71, 18);
            this.lblPot8Title.TabIndex = 36;
            this.lblPot8Title.Text = "Side Pot 8:";
            this.lblPot8Title.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot8Title.Visible = false;
            // 
            // lblPot7Title
            // 
            this.lblPot7Title.BackColor = System.Drawing.Color.Black;
            this.lblPot7Title.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot7Title.ForeColor = System.Drawing.Color.White;
            this.lblPot7Title.Location = new System.Drawing.Point(12, 467);
            this.lblPot7Title.Name = "lblPot7Title";
            this.lblPot7Title.Size = new System.Drawing.Size(71, 18);
            this.lblPot7Title.TabIndex = 35;
            this.lblPot7Title.Text = "Side Pot 7:";
            this.lblPot7Title.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot7Title.Visible = false;
            // 
            // lblPot6Title
            // 
            this.lblPot6Title.BackColor = System.Drawing.Color.Black;
            this.lblPot6Title.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot6Title.ForeColor = System.Drawing.Color.White;
            this.lblPot6Title.Location = new System.Drawing.Point(12, 449);
            this.lblPot6Title.Name = "lblPot6Title";
            this.lblPot6Title.Size = new System.Drawing.Size(71, 18);
            this.lblPot6Title.TabIndex = 34;
            this.lblPot6Title.Text = "Side Pot 6:";
            this.lblPot6Title.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot6Title.Visible = false;
            // 
            // lblPot5Title
            // 
            this.lblPot5Title.BackColor = System.Drawing.Color.Black;
            this.lblPot5Title.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot5Title.ForeColor = System.Drawing.Color.White;
            this.lblPot5Title.Location = new System.Drawing.Point(12, 431);
            this.lblPot5Title.Name = "lblPot5Title";
            this.lblPot5Title.Size = new System.Drawing.Size(71, 18);
            this.lblPot5Title.TabIndex = 33;
            this.lblPot5Title.Text = "Side Pot 5:";
            this.lblPot5Title.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot5Title.Visible = false;
            // 
            // lblPot4Title
            // 
            this.lblPot4Title.BackColor = System.Drawing.Color.Black;
            this.lblPot4Title.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot4Title.ForeColor = System.Drawing.Color.White;
            this.lblPot4Title.Location = new System.Drawing.Point(12, 81);
            this.lblPot4Title.Name = "lblPot4Title";
            this.lblPot4Title.Size = new System.Drawing.Size(71, 18);
            this.lblPot4Title.TabIndex = 32;
            this.lblPot4Title.Text = "Side Pot 4:";
            this.lblPot4Title.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot4Title.Visible = false;
            // 
            // lblPot3Title
            // 
            this.lblPot3Title.BackColor = System.Drawing.Color.Black;
            this.lblPot3Title.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot3Title.ForeColor = System.Drawing.Color.White;
            this.lblPot3Title.Location = new System.Drawing.Point(12, 63);
            this.lblPot3Title.Name = "lblPot3Title";
            this.lblPot3Title.Size = new System.Drawing.Size(71, 18);
            this.lblPot3Title.TabIndex = 31;
            this.lblPot3Title.Text = "Side Pot 3:";
            this.lblPot3Title.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot3Title.Visible = false;
            // 
            // lblPot2Title
            // 
            this.lblPot2Title.BackColor = System.Drawing.Color.Black;
            this.lblPot2Title.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot2Title.ForeColor = System.Drawing.Color.White;
            this.lblPot2Title.Location = new System.Drawing.Point(12, 45);
            this.lblPot2Title.Name = "lblPot2Title";
            this.lblPot2Title.Size = new System.Drawing.Size(71, 18);
            this.lblPot2Title.TabIndex = 30;
            this.lblPot2Title.Text = "Side Pot 2:";
            this.lblPot2Title.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot2Title.Visible = false;
            // 
            // lblPot1Title
            // 
            this.lblPot1Title.BackColor = System.Drawing.Color.Black;
            this.lblPot1Title.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot1Title.ForeColor = System.Drawing.Color.White;
            this.lblPot1Title.Location = new System.Drawing.Point(12, 27);
            this.lblPot1Title.Name = "lblPot1Title";
            this.lblPot1Title.Size = new System.Drawing.Size(71, 18);
            this.lblPot1Title.TabIndex = 29;
            this.lblPot1Title.Text = "Side Pot 1:";
            this.lblPot1Title.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot1Title.Visible = false;
            // 
            // lblPot0Title
            // 
            this.lblPot0Title.BackColor = System.Drawing.Color.Black;
            this.lblPot0Title.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPot0Title.ForeColor = System.Drawing.Color.White;
            this.lblPot0Title.Location = new System.Drawing.Point(12, 9);
            this.lblPot0Title.Name = "lblPot0Title";
            this.lblPot0Title.Size = new System.Drawing.Size(71, 18);
            this.lblPot0Title.TabIndex = 28;
            this.lblPot0Title.Text = "Main Pot:";
            this.lblPot0Title.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblPot0Title.Visible = false;
            // 
            // label10
            // 
            this.label10.BackColor = System.Drawing.Color.Transparent;
            this.label10.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label10.ForeColor = System.Drawing.Color.White;
            this.label10.Location = new System.Drawing.Point(202, 203);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(100, 16);
            this.label10.TabIndex = 27;
            this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label9
            // 
            this.label9.BackColor = System.Drawing.Color.Transparent;
            this.label9.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label9.ForeColor = System.Drawing.Color.White;
            this.label9.Location = new System.Drawing.Point(202, 326);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(100, 16);
            this.label9.TabIndex = 26;
            this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label8
            // 
            this.label8.BackColor = System.Drawing.Color.Transparent;
            this.label8.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label8.ForeColor = System.Drawing.Color.White;
            this.label8.Location = new System.Drawing.Point(237, 374);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(100, 16);
            this.label8.TabIndex = 25;
            this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label7
            // 
            this.label7.BackColor = System.Drawing.Color.Transparent;
            this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label7.ForeColor = System.Drawing.Color.White;
            this.label7.Location = new System.Drawing.Point(382, 374);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(100, 16);
            this.label7.TabIndex = 24;
            this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label6
            // 
            this.label6.BackColor = System.Drawing.Color.Transparent;
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label6.ForeColor = System.Drawing.Color.White;
            this.label6.Location = new System.Drawing.Point(527, 374);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(100, 16);
            this.label6.TabIndex = 23;
            this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label5
            // 
            this.label5.BackColor = System.Drawing.Color.Transparent;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.ForeColor = System.Drawing.Color.White;
            this.label5.Location = new System.Drawing.Point(563, 326);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(100, 16);
            this.label5.TabIndex = 22;
            this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label4
            // 
            this.label4.BackColor = System.Drawing.Color.Transparent;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.ForeColor = System.Drawing.Color.White;
            this.label4.Location = new System.Drawing.Point(563, 203);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(100, 16);
            this.label4.TabIndex = 21;
            this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label3
            // 
            this.label3.BackColor = System.Drawing.Color.Transparent;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.ForeColor = System.Drawing.Color.White;
            this.label3.Location = new System.Drawing.Point(527, 140);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(100, 16);
            this.label3.TabIndex = 20;
            this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label2
            // 
            this.label2.BackColor = System.Drawing.Color.Transparent;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.ForeColor = System.Drawing.Color.White;
            this.label2.Location = new System.Drawing.Point(382, 140);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(100, 16);
            this.label2.TabIndex = 19;
            this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label1
            // 
            this.label1.BackColor = System.Drawing.Color.Transparent;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.ForeColor = System.Drawing.Color.White;
            this.label1.Location = new System.Drawing.Point(237, 140);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(100, 16);
            this.label1.TabIndex = 18;
            this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // cardPictureBox5
            // 
            this.cardPictureBox5.BackColor = System.Drawing.Color.Transparent;
            this.cardPictureBox5.Card = null;
            this.cardPictureBox5.Location = new System.Drawing.Point(549, 240);
            this.cardPictureBox5.Name = "cardPictureBox5";
            this.cardPictureBox5.Size = new System.Drawing.Size(40, 56);
            this.cardPictureBox5.TabIndex = 17;
            this.cardPictureBox5.TabStop = false;
            // 
            // cardPictureBox4
            // 
            this.cardPictureBox4.BackColor = System.Drawing.Color.Transparent;
            this.cardPictureBox4.Card = null;
            this.cardPictureBox4.Location = new System.Drawing.Point(469, 240);
            this.cardPictureBox4.Name = "cardPictureBox4";
            this.cardPictureBox4.Size = new System.Drawing.Size(40, 56);
            this.cardPictureBox4.TabIndex = 16;
            this.cardPictureBox4.TabStop = false;
            // 
            // cardPictureBox3
            // 
            this.cardPictureBox3.BackColor = System.Drawing.Color.Transparent;
            this.cardPictureBox3.Card = null;
            this.cardPictureBox3.Location = new System.Drawing.Point(389, 240);
            this.cardPictureBox3.Name = "cardPictureBox3";
            this.cardPictureBox3.Size = new System.Drawing.Size(40, 56);
            this.cardPictureBox3.TabIndex = 15;
            this.cardPictureBox3.TabStop = false;
            // 
            // cardPictureBox2
            // 
            this.cardPictureBox2.BackColor = System.Drawing.Color.Transparent;
            this.cardPictureBox2.Card = null;
            this.cardPictureBox2.Location = new System.Drawing.Point(329, 240);
            this.cardPictureBox2.Name = "cardPictureBox2";
            this.cardPictureBox2.Size = new System.Drawing.Size(40, 56);
            this.cardPictureBox2.TabIndex = 14;
            this.cardPictureBox2.TabStop = false;
            // 
            // cardPictureBox1
            // 
            this.cardPictureBox1.BackColor = System.Drawing.Color.Transparent;
            this.cardPictureBox1.Card = null;
            this.cardPictureBox1.Location = new System.Drawing.Point(269, 240);
            this.cardPictureBox1.Name = "cardPictureBox1";
            this.cardPictureBox1.Size = new System.Drawing.Size(40, 56);
            this.cardPictureBox1.TabIndex = 13;
            this.cardPictureBox1.TabStop = false;
            // 
            // pokerPlayerHud10
            // 
            this.pokerPlayerHud10.Alive = false;
            this.pokerPlayerHud10.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(82)))), ((int)(((byte)(98)))), ((int)(((byte)(114)))));
            this.pokerPlayerHud10.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.pokerPlayerHud10.Location = new System.Drawing.Point(75, 130);
            this.pokerPlayerHud10.Main = false;
            this.pokerPlayerHud10.Name = "pokerPlayerHud10";
            this.pokerPlayerHud10.PlayerName = "Player Name";
            this.pokerPlayerHud10.Size = new System.Drawing.Size(121, 121);
            this.pokerPlayerHud10.TabIndex = 12;
            this.pokerPlayerHud10.Visible = false;
            // 
            // pokerPlayerHud9
            // 
            this.pokerPlayerHud9.Alive = false;
            this.pokerPlayerHud9.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(82)))), ((int)(((byte)(98)))), ((int)(((byte)(114)))));
            this.pokerPlayerHud9.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.pokerPlayerHud9.Location = new System.Drawing.Point(75, 275);
            this.pokerPlayerHud9.Main = false;
            this.pokerPlayerHud9.Name = "pokerPlayerHud9";
            this.pokerPlayerHud9.PlayerName = "Player Name";
            this.pokerPlayerHud9.Size = new System.Drawing.Size(121, 121);
            this.pokerPlayerHud9.TabIndex = 11;
            this.pokerPlayerHud9.Visible = false;
            // 
            // pokerPlayerHud8
            // 
            this.pokerPlayerHud8.Alive = false;
            this.pokerPlayerHud8.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(82)))), ((int)(((byte)(98)))), ((int)(((byte)(114)))));
            this.pokerPlayerHud8.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.pokerPlayerHud8.Location = new System.Drawing.Point(225, 400);
            this.pokerPlayerHud8.Main = false;
            this.pokerPlayerHud8.Name = "pokerPlayerHud8";
            this.pokerPlayerHud8.PlayerName = "Player Name";
            this.pokerPlayerHud8.Size = new System.Drawing.Size(121, 121);
            this.pokerPlayerHud8.TabIndex = 10;
            this.pokerPlayerHud8.Visible = false;
            // 
            // pokerPlayerHud7
            // 
            this.pokerPlayerHud7.Alive = false;
            this.pokerPlayerHud7.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(82)))), ((int)(((byte)(98)))), ((int)(((byte)(114)))));
            this.pokerPlayerHud7.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.pokerPlayerHud7.Location = new System.Drawing.Point(370, 400);
            this.pokerPlayerHud7.Main = false;
            this.pokerPlayerHud7.Name = "pokerPlayerHud7";
            this.pokerPlayerHud7.PlayerName = "Player Name";
            this.pokerPlayerHud7.Size = new System.Drawing.Size(121, 121);
            this.pokerPlayerHud7.TabIndex = 9;
            this.pokerPlayerHud7.Visible = false;
            // 
            // pokerPlayerHud6
            // 
            this.pokerPlayerHud6.Alive = false;
            this.pokerPlayerHud6.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(82)))), ((int)(((byte)(98)))), ((int)(((byte)(114)))));
            this.pokerPlayerHud6.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.pokerPlayerHud6.Location = new System.Drawing.Point(515, 400);
            this.pokerPlayerHud6.Main = false;
            this.pokerPlayerHud6.Name = "pokerPlayerHud6";
            this.pokerPlayerHud6.PlayerName = "Player Name";
            this.pokerPlayerHud6.Size = new System.Drawing.Size(121, 121);
            this.pokerPlayerHud6.TabIndex = 8;
            this.pokerPlayerHud6.Visible = false;
            // 
            // pokerPlayerHud5
            // 
            this.pokerPlayerHud5.Alive = false;
            this.pokerPlayerHud5.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(82)))), ((int)(((byte)(98)))), ((int)(((byte)(114)))));
            this.pokerPlayerHud5.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.pokerPlayerHud5.Location = new System.Drawing.Point(665, 275);
            this.pokerPlayerHud5.Main = false;
            this.pokerPlayerHud5.Name = "pokerPlayerHud5";
            this.pokerPlayerHud5.PlayerName = "Player Name";
            this.pokerPlayerHud5.Size = new System.Drawing.Size(121, 121);
            this.pokerPlayerHud5.TabIndex = 7;
            this.pokerPlayerHud5.Visible = false;
            // 
            // pokerPlayerHud4
            // 
            this.pokerPlayerHud4.Alive = false;
            this.pokerPlayerHud4.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(82)))), ((int)(((byte)(98)))), ((int)(((byte)(114)))));
            this.pokerPlayerHud4.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.pokerPlayerHud4.Location = new System.Drawing.Point(665, 130);
            this.pokerPlayerHud4.Main = false;
            this.pokerPlayerHud4.Name = "pokerPlayerHud4";
            this.pokerPlayerHud4.PlayerName = "Player Name";
            this.pokerPlayerHud4.Size = new System.Drawing.Size(121, 121);
            this.pokerPlayerHud4.TabIndex = 6;
            this.pokerPlayerHud4.Visible = false;
            // 
            // pokerPlayerHud3
            // 
            this.pokerPlayerHud3.Alive = false;
            this.pokerPlayerHud3.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(82)))), ((int)(((byte)(98)))), ((int)(((byte)(114)))));
            this.pokerPlayerHud3.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.pokerPlayerHud3.Location = new System.Drawing.Point(515, 10);
            this.pokerPlayerHud3.Main = false;
            this.pokerPlayerHud3.Name = "pokerPlayerHud3";
            this.pokerPlayerHud3.PlayerName = "Player Name";
            this.pokerPlayerHud3.Size = new System.Drawing.Size(121, 121);
            this.pokerPlayerHud3.TabIndex = 5;
            this.pokerPlayerHud3.Visible = false;
            // 
            // pokerPlayerHud2
            // 
            this.pokerPlayerHud2.Alive = false;
            this.pokerPlayerHud2.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(82)))), ((int)(((byte)(98)))), ((int)(((byte)(114)))));
            this.pokerPlayerHud2.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.pokerPlayerHud2.Location = new System.Drawing.Point(370, 10);
            this.pokerPlayerHud2.Main = false;
            this.pokerPlayerHud2.Name = "pokerPlayerHud2";
            this.pokerPlayerHud2.PlayerName = "Player Name";
            this.pokerPlayerHud2.Size = new System.Drawing.Size(121, 121);
            this.pokerPlayerHud2.TabIndex = 3;
            this.pokerPlayerHud2.Visible = false;
            // 
            // pokerPlayerHud1
            // 
            this.pokerPlayerHud1.Alive = false;
            this.pokerPlayerHud1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(82)))), ((int)(((byte)(98)))), ((int)(((byte)(114)))));
            this.pokerPlayerHud1.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.pokerPlayerHud1.Location = new System.Drawing.Point(225, 10);
            this.pokerPlayerHud1.Main = false;
            this.pokerPlayerHud1.Name = "pokerPlayerHud1";
            this.pokerPlayerHud1.PlayerName = "Player Name";
            this.pokerPlayerHud1.Size = new System.Drawing.Size(121, 121);
            this.pokerPlayerHud1.TabIndex = 2;
            this.pokerPlayerHud1.Visible = false;
            // 
            // lblTotalPot
            // 
            this.lblTotalPot.BackColor = System.Drawing.Color.Black;
            this.lblTotalPot.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblTotalPot.ForeColor = System.Drawing.Color.White;
            this.lblTotalPot.Location = new System.Drawing.Point(715, 63);
            this.lblTotalPot.Name = "lblTotalPot";
            this.lblTotalPot.Size = new System.Drawing.Size(100, 18);
            this.lblTotalPot.TabIndex = 1;
            this.lblTotalPot.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // lblTotalPotTitle
            // 
            this.lblTotalPotTitle.BackColor = System.Drawing.Color.Black;
            this.lblTotalPotTitle.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblTotalPotTitle.ForeColor = System.Drawing.Color.White;
            this.lblTotalPotTitle.Location = new System.Drawing.Point(715, 45);
            this.lblTotalPotTitle.Name = "lblTotalPotTitle";
            this.lblTotalPotTitle.Size = new System.Drawing.Size(100, 18);
            this.lblTotalPotTitle.TabIndex = 0;
            this.lblTotalPotTitle.Text = "Total Pot:";
            this.lblTotalPotTitle.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // TableViewerForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.DimGray;
            this.ClientSize = new System.Drawing.Size(1018, 687);
            this.Controls.Add(this.logConsole);
            this.Controls.Add(this.btnHelp);
            this.Controls.Add(this.pnlTable);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "TableViewerForm";
            this.Text = "C# Poker Table (Viewer Only)";
            this.pnlTable.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.cardPictureBox5)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.cardPictureBox4)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.cardPictureBox3)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.cardPictureBox2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.cardPictureBox1)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel pnlTable;
        private PokerPlayerHud pokerPlayerHud3;
        private PokerPlayerHud pokerPlayerHud2;
        private PokerPlayerHud pokerPlayerHud1;
        private System.Windows.Forms.Label lblTotalPot;
        private System.Windows.Forms.Label lblTotalPotTitle;
        private PokerPlayerHud pokerPlayerHud10;
        private PokerPlayerHud pokerPlayerHud9;
        private PokerPlayerHud pokerPlayerHud8;
        private PokerPlayerHud pokerPlayerHud7;
        private PokerPlayerHud pokerPlayerHud6;
        private PokerPlayerHud pokerPlayerHud5;
        private PokerPlayerHud pokerPlayerHud4;
        private System.Windows.Forms.Label label1;
        private CardPictureBox cardPictureBox5;
        private CardPictureBox cardPictureBox4;
        private CardPictureBox cardPictureBox3;
        private CardPictureBox cardPictureBox2;
        private CardPictureBox cardPictureBox1;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label2;
        private VIBlend.WinForms.Controls.vButton btnHelp;
        private LogConsole logConsole;
        private System.Windows.Forms.Label lblPot9;
        private System.Windows.Forms.Label lblPot8;
        private System.Windows.Forms.Label lblPot7;
        private System.Windows.Forms.Label lblPot6;
        private System.Windows.Forms.Label lblPot5;
        private System.Windows.Forms.Label lblPot0;
        private System.Windows.Forms.Label lblPot1;
        private System.Windows.Forms.Label lblPot2;
        private System.Windows.Forms.Label lblPot3;
        private System.Windows.Forms.Label lblPot4;
        private System.Windows.Forms.Label lblPot9Title;
        private System.Windows.Forms.Label lblPot8Title;
        private System.Windows.Forms.Label lblPot7Title;
        private System.Windows.Forms.Label lblPot6Title;
        private System.Windows.Forms.Label lblPot5Title;
        private System.Windows.Forms.Label lblPot4Title;
        private System.Windows.Forms.Label lblPot3Title;
        private System.Windows.Forms.Label lblPot2Title;
        private System.Windows.Forms.Label lblPot1Title;
        private System.Windows.Forms.Label lblPot0Title;
    }
}