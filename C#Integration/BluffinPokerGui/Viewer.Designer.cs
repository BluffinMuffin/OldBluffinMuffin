namespace BluffinPokerGui
{
    partial class Viewer
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
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.numericUpDown1 = new System.Windows.Forms.NumericUpDown();
            this.numericUpDown2 = new System.Windows.Forms.NumericUpDown();
            this.panel1 = new System.Windows.Forms.Panel();
            this.pokerPlayerHud1 = new BluffinPokerGui.Game.PokerPlayerHud();
            this.cardPictureBox1 = new BluffinPokerGui.Game.CardPictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown2)).BeginInit();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.cardPictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // pictureBox1
            // 
            this.pictureBox1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.pictureBox1.Location = new System.Drawing.Point(12, 12);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(57, 75);
            this.pictureBox1.TabIndex = 1;
            this.pictureBox1.TabStop = false;
            // 
            // numericUpDown1
            // 
            this.numericUpDown1.Location = new System.Drawing.Point(12, 105);
            this.numericUpDown1.Maximum = new decimal(new int[] {
            3,
            0,
            0,
            0});
            this.numericUpDown1.Name = "numericUpDown1";
            this.numericUpDown1.Size = new System.Drawing.Size(120, 20);
            this.numericUpDown1.TabIndex = 2;
            this.numericUpDown1.ValueChanged += new System.EventHandler(this.numericUpDown1_ValueChanged);
            // 
            // numericUpDown2
            // 
            this.numericUpDown2.Location = new System.Drawing.Point(12, 131);
            this.numericUpDown2.Maximum = new decimal(new int[] {
            12,
            0,
            0,
            0});
            this.numericUpDown2.Name = "numericUpDown2";
            this.numericUpDown2.Size = new System.Drawing.Size(120, 20);
            this.numericUpDown2.TabIndex = 3;
            this.numericUpDown2.ValueChanged += new System.EventHandler(this.numericUpDown1_ValueChanged);
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.Color.Black;
            this.panel1.BackgroundImage = global::BluffinPokerGui.Properties.Resources.table;
            this.panel1.Controls.Add(this.pokerPlayerHud1);
            this.panel1.Controls.Add(this.cardPictureBox1);
            this.panel1.Location = new System.Drawing.Point(138, 12);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(874, 556);
            this.panel1.TabIndex = 5;
            // 
            // pokerPlayerHud1
            // 
            this.pokerPlayerHud1.Alive = true;
            this.pokerPlayerHud1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(82)))), ((int)(((byte)(98)))), ((int)(((byte)(114)))));
            this.pokerPlayerHud1.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.pokerPlayerHud1.Location = new System.Drawing.Point(158, 59);
            this.pokerPlayerHud1.Main = false;
            this.pokerPlayerHud1.Name = "pokerPlayerHud1";
            this.pokerPlayerHud1.PlayerName = "Player Name";
            this.pokerPlayerHud1.Size = new System.Drawing.Size(121, 121);
            this.pokerPlayerHud1.TabIndex = 6;
            // 
            // cardPictureBox1
            // 
            this.cardPictureBox1.BackColor = System.Drawing.Color.Transparent;
            this.cardPictureBox1.Card = null;
            this.cardPictureBox1.Location = new System.Drawing.Point(299, 241);
            this.cardPictureBox1.Name = "cardPictureBox1";
            this.cardPictureBox1.Size = new System.Drawing.Size(40, 56);
            this.cardPictureBox1.TabIndex = 5;
            this.cardPictureBox1.TabStop = false;
            // 
            // Viewer
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1027, 579);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.numericUpDown2);
            this.Controls.Add(this.numericUpDown1);
            this.Controls.Add(this.pictureBox1);
            this.Name = "Viewer";
            this.Text = "Viewer";
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown2)).EndInit();
            this.panel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.cardPictureBox1)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.NumericUpDown numericUpDown1;
        private System.Windows.Forms.NumericUpDown numericUpDown2;
        private System.Windows.Forms.Panel panel1;
        private BluffinPokerGui.Game.CardPictureBox cardPictureBox1;
        private BluffinPokerGui.Game.PokerPlayerHud pokerPlayerHud1;
    }
}