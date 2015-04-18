using System.Windows.Forms;

namespace BluffinMuffin.Client.Game
{
    partial class BuyInForm
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(BuyInForm));
            this.lblAccountMoney = new System.Windows.Forms.Label();
            this.label11 = new System.Windows.Forms.Label();
            this.lblMoneyUnit = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.lblMin = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.lblMax = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.nudBuyIn = new System.Windows.Forms.NumericUpDown();
            this.label7 = new System.Windows.Forms.Label();
            this.btnSitIn = new Button();
            ((System.ComponentModel.ISupportInitialize)(this.nudBuyIn)).BeginInit();
            this.SuspendLayout();
            // 
            // lblAccountMoney
            // 
            this.lblAccountMoney.AutoSize = true;
            this.lblAccountMoney.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.lblAccountMoney.Location = new System.Drawing.Point(158, 9);
            this.lblAccountMoney.Name = "lblAccountMoney";
            this.lblAccountMoney.Size = new System.Drawing.Size(56, 17);
            this.lblAccountMoney.TabIndex = 38;
            this.lblAccountMoney.Text = "$10042";
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold);
            this.label11.Location = new System.Drawing.Point(12, 9);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(140, 17);
            this.label11.TabIndex = 37;
            this.label11.Text = "Money in account:";
            // 
            // lblMoneyUnit
            // 
            this.lblMoneyUnit.AutoSize = true;
            this.lblMoneyUnit.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.lblMoneyUnit.Location = new System.Drawing.Point(169, 37);
            this.lblMoneyUnit.Name = "lblMoneyUnit";
            this.lblMoneyUnit.Size = new System.Drawing.Size(32, 17);
            this.lblMoneyUnit.TabIndex = 40;
            this.lblMoneyUnit.Text = "$10";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold);
            this.label2.Location = new System.Drawing.Point(12, 37);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(151, 17);
            this.label2.TabIndex = 39;
            this.label2.Text = "Current money unit:";
            // 
            // lblMin
            // 
            this.lblMin.AutoSize = true;
            this.lblMin.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.lblMin.Location = new System.Drawing.Point(144, 65);
            this.lblMin.Name = "lblMin";
            this.lblMin.Size = new System.Drawing.Size(40, 17);
            this.lblMin.TabIndex = 42;
            this.lblMin.Text = "$200";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold);
            this.label3.Location = new System.Drawing.Point(12, 65);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(126, 17);
            this.label3.TabIndex = 41;
            this.label3.Text = "Minimum Buy-in:";
            // 
            // lblMax
            // 
            this.lblMax.AutoSize = true;
            this.lblMax.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.lblMax.Location = new System.Drawing.Point(147, 94);
            this.lblMax.Name = "lblMax";
            this.lblMax.Size = new System.Drawing.Size(40, 17);
            this.lblMax.TabIndex = 44;
            this.lblMax.Text = "$200";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold);
            this.label5.Location = new System.Drawing.Point(12, 94);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(129, 17);
            this.label5.TabIndex = 43;
            this.label5.Text = "Maximum Buy-in:";
            // 
            // nudBuyIn
            // 
            this.nudBuyIn.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.nudBuyIn.Increment = new decimal(new int[] {
            500,
            0,
            0,
            0});
            this.nudBuyIn.Location = new System.Drawing.Point(81, 146);
            this.nudBuyIn.Maximum = new decimal(new int[] {
            60000,
            0,
            0,
            0});
            this.nudBuyIn.Name = "nudBuyIn";
            this.nudBuyIn.Size = new System.Drawing.Size(61, 23);
            this.nudBuyIn.TabIndex = 46;
            this.nudBuyIn.Value = new decimal(new int[] {
            500,
            0,
            0,
            0});
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold);
            this.label7.Location = new System.Drawing.Point(64, 126);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(97, 17);
            this.label7.TabIndex = 45;
            this.label7.Text = "Your buy-in:";
            // 
            // btnSitIn
            // 
            this.btnSitIn.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.btnSitIn.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnSitIn.Image = global::BluffinMuffin.Client.Properties.Resources.sit_in;
            this.btnSitIn.Location = new System.Drawing.Point(58, 175);
            this.btnSitIn.Name = "btnSitIn";
            this.btnSitIn.Size = new System.Drawing.Size(105, 25);
            this.btnSitIn.TabIndex = 47;
            this.btnSitIn.Text = "Sit In";
            this.btnSitIn.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.btnSitIn.UseVisualStyleBackColor = true;
            this.btnSitIn.Click += new System.EventHandler(this.btnSitIn_Click);
            // 
            // BuyInForm
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
            this.BackColor = System.Drawing.Color.White;
            this.ClientSize = new System.Drawing.Size(222, 212);
            this.Controls.Add(this.btnSitIn);
            this.Controls.Add(this.nudBuyIn);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.lblMax);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.lblMin);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.lblMoneyUnit);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.lblAccountMoney);
            this.Controls.Add(this.label11);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedToolWindow;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "BuyInForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Buy-In";
            ((System.ComponentModel.ISupportInitialize)(this.nudBuyIn)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lblAccountMoney;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.Label lblMoneyUnit;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label lblMin;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label lblMax;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.NumericUpDown nudBuyIn;
        private System.Windows.Forms.Label label7;
        private Button btnSitIn;
    }
}