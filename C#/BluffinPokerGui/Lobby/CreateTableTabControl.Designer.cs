namespace BluffinPokerGUI.Lobby
{
    partial class CreateTableTabControl
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

        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.lstVariant = new System.Windows.Forms.ComboBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.txtTableName = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.nudNbPlayersMin = new System.Windows.Forms.NumericUpDown();
            this.nudNbPlayersMax = new System.Windows.Forms.NumericUpDown();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.lstBetLimit = new System.Windows.Forms.ComboBox();
            this.label6 = new System.Windows.Forms.Label();
            this.lstBlinds = new System.Windows.Forms.ComboBox();
            this.grpTimes = new System.Windows.Forms.GroupBox();
            this.nudWTAPotWon = new System.Windows.Forms.NumericUpDown();
            this.nudWTABoardDealed = new System.Windows.Forms.NumericUpDown();
            this.label9 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.nudWTAPlayerAction = new System.Windows.Forms.NumericUpDown();
            this.label7 = new System.Windows.Forms.Label();
            this.grpTraining = new System.Windows.Forms.GroupBox();
            this.nudStartingAmount = new System.Windows.Forms.NumericUpDown();
            this.label12 = new System.Windows.Forms.Label();
            this.nudBlind = new System.Windows.Forms.NumericUpDown();
            this.lblBlind = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.nudNbPlayersMin)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudNbPlayersMax)).BeginInit();
            this.grpTimes.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.nudWTAPotWon)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudWTABoardDealed)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudWTAPlayerAction)).BeginInit();
            this.grpTraining.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.nudStartingAmount)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudBlind)).BeginInit();
            this.SuspendLayout();
            // 
            // lstVariant
            // 
            this.lstVariant.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.lstVariant.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lstVariant.FormattingEnabled = true;
            this.lstVariant.Location = new System.Drawing.Point(285, 11);
            this.lstVariant.Name = "lstVariant";
            this.lstVariant.Size = new System.Drawing.Size(180, 24);
            this.lstVariant.Sorted = true;
            this.lstVariant.TabIndex = 0;
            this.lstVariant.SelectedIndexChanged += new System.EventHandler(this.lstVariant_SelectedIndexChanged);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.label1.Location = new System.Drawing.Point(222, 14);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(57, 17);
            this.label1.TabIndex = 1;
            this.label1.Text = "Variant:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.label2.Location = new System.Drawing.Point(3, 14);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(49, 17);
            this.label2.TabIndex = 2;
            this.label2.Text = "Name:";
            // 
            // txtTableName
            // 
            this.txtTableName.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.txtTableName.Location = new System.Drawing.Point(58, 11);
            this.txtTableName.Name = "txtTableName";
            this.txtTableName.Size = new System.Drawing.Size(158, 23);
            this.txtTableName.TabIndex = 3;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.label3.Location = new System.Drawing.Point(3, 43);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(167, 17);
            this.label3.TabIndex = 4;
            this.label3.Text = "Minimum players to Start:";
            // 
            // nudNbPlayersMin
            // 
            this.nudNbPlayersMin.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.nudNbPlayersMin.Location = new System.Drawing.Point(176, 41);
            this.nudNbPlayersMin.Maximum = new decimal(new int[] {
            10,
            0,
            0,
            0});
            this.nudNbPlayersMin.Minimum = new decimal(new int[] {
            2,
            0,
            0,
            0});
            this.nudNbPlayersMin.Name = "nudNbPlayersMin";
            this.nudNbPlayersMin.Size = new System.Drawing.Size(40, 23);
            this.nudNbPlayersMin.TabIndex = 19;
            this.nudNbPlayersMin.Value = new decimal(new int[] {
            2,
            0,
            0,
            0});
            this.nudNbPlayersMin.ValueChanged += new System.EventHandler(this.nudNbPlayersMin_ValueChanged);
            // 
            // nudNbPlayersMax
            // 
            this.nudNbPlayersMax.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.nudNbPlayersMax.Location = new System.Drawing.Point(425, 41);
            this.nudNbPlayersMax.Maximum = new decimal(new int[] {
            10,
            0,
            0,
            0});
            this.nudNbPlayersMax.Minimum = new decimal(new int[] {
            2,
            0,
            0,
            0});
            this.nudNbPlayersMax.Name = "nudNbPlayersMax";
            this.nudNbPlayersMax.Size = new System.Drawing.Size(40, 23);
            this.nudNbPlayersMax.TabIndex = 21;
            this.nudNbPlayersMax.Value = new decimal(new int[] {
            10,
            0,
            0,
            0});
            this.nudNbPlayersMax.ValueChanged += new System.EventHandler(this.nudNbPlayersMax_ValueChanged);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.label4.Location = new System.Drawing.Point(299, 43);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(120, 17);
            this.label4.TabIndex = 20;
            this.label4.Text = "Maximum players:";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.label5.Location = new System.Drawing.Point(6, 73);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(61, 17);
            this.label5.TabIndex = 23;
            this.label5.Text = "Bet limit:";
            // 
            // lstBetLimit
            // 
            this.lstBetLimit.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.lstBetLimit.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lstBetLimit.FormattingEnabled = true;
            this.lstBetLimit.Location = new System.Drawing.Point(73, 70);
            this.lstBetLimit.Name = "lstBetLimit";
            this.lstBetLimit.Size = new System.Drawing.Size(143, 24);
            this.lstBetLimit.Sorted = true;
            this.lstBetLimit.TabIndex = 22;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.label6.Location = new System.Drawing.Point(249, 73);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(86, 17);
            this.label6.TabIndex = 25;
            this.label6.Text = "Starting Bet:";
            // 
            // lstBlinds
            // 
            this.lstBlinds.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.lstBlinds.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lstBlinds.FormattingEnabled = true;
            this.lstBlinds.Location = new System.Drawing.Point(341, 70);
            this.lstBlinds.Name = "lstBlinds";
            this.lstBlinds.Size = new System.Drawing.Size(124, 24);
            this.lstBlinds.Sorted = true;
            this.lstBlinds.TabIndex = 24;
            this.lstBlinds.SelectedIndexChanged += new System.EventHandler(this.lstBlinds_SelectedIndexChanged);
            // 
            // grpTimes
            // 
            this.grpTimes.Controls.Add(this.nudWTAPotWon);
            this.grpTimes.Controls.Add(this.nudWTABoardDealed);
            this.grpTimes.Controls.Add(this.label9);
            this.grpTimes.Controls.Add(this.label8);
            this.grpTimes.Controls.Add(this.nudWTAPlayerAction);
            this.grpTimes.Controls.Add(this.label7);
            this.grpTimes.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.grpTimes.Location = new System.Drawing.Point(3, 138);
            this.grpTimes.Name = "grpTimes";
            this.grpTimes.Size = new System.Drawing.Size(459, 74);
            this.grpTimes.TabIndex = 26;
            this.grpTimes.TabStop = false;
            this.grpTimes.Text = "Waiting Time after ... (ms)";
            // 
            // nudWTAPotWon
            // 
            this.nudWTAPotWon.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.nudWTAPotWon.Increment = new decimal(new int[] {
            500,
            0,
            0,
            0});
            this.nudWTAPotWon.Location = new System.Drawing.Point(352, 39);
            this.nudWTAPotWon.Maximum = new decimal(new int[] {
            60000,
            0,
            0,
            0});
            this.nudWTAPotWon.Name = "nudWTAPotWon";
            this.nudWTAPotWon.Size = new System.Drawing.Size(61, 23);
            this.nudWTAPotWon.TabIndex = 23;
            this.nudWTAPotWon.Value = new decimal(new int[] {
            2500,
            0,
            0,
            0});
            // 
            // nudWTABoardDealed
            // 
            this.nudWTABoardDealed.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.nudWTABoardDealed.Increment = new decimal(new int[] {
            500,
            0,
            0,
            0});
            this.nudWTABoardDealed.Location = new System.Drawing.Point(212, 39);
            this.nudWTABoardDealed.Maximum = new decimal(new int[] {
            60000,
            0,
            0,
            0});
            this.nudWTABoardDealed.Name = "nudWTABoardDealed";
            this.nudWTABoardDealed.Size = new System.Drawing.Size(61, 23);
            this.nudWTABoardDealed.TabIndex = 23;
            this.nudWTABoardDealed.Value = new decimal(new int[] {
            500,
            0,
            0,
            0});
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.label9.Location = new System.Drawing.Point(347, 19);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(66, 17);
            this.label9.TabIndex = 22;
            this.label9.Text = "Pot Won:";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.label8.Location = new System.Drawing.Point(174, 19);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(99, 17);
            this.label8.TabIndex = 22;
            this.label8.Text = "Board Dealed:";
            // 
            // nudWTAPlayerAction
            // 
            this.nudWTAPlayerAction.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.nudWTAPlayerAction.Increment = new decimal(new int[] {
            500,
            0,
            0,
            0});
            this.nudWTAPlayerAction.Location = new System.Drawing.Point(52, 39);
            this.nudWTAPlayerAction.Maximum = new decimal(new int[] {
            60000,
            0,
            0,
            0});
            this.nudWTAPlayerAction.Name = "nudWTAPlayerAction";
            this.nudWTAPlayerAction.Size = new System.Drawing.Size(61, 23);
            this.nudWTAPlayerAction.TabIndex = 21;
            this.nudWTAPlayerAction.Value = new decimal(new int[] {
            500,
            0,
            0,
            0});
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.label7.Location = new System.Drawing.Point(18, 19);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(95, 17);
            this.label7.TabIndex = 20;
            this.label7.Text = "Player Action:";
            // 
            // grpTraining
            // 
            this.grpTraining.Controls.Add(this.nudStartingAmount);
            this.grpTraining.Controls.Add(this.label12);
            this.grpTraining.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.grpTraining.Location = new System.Drawing.Point(3, 218);
            this.grpTraining.Name = "grpTraining";
            this.grpTraining.Size = new System.Drawing.Size(459, 53);
            this.grpTraining.TabIndex = 27;
            this.grpTraining.TabStop = false;
            this.grpTraining.Text = "Training Options";
            // 
            // nudStartingAmount
            // 
            this.nudStartingAmount.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.nudStartingAmount.Increment = new decimal(new int[] {
            100,
            0,
            0,
            0});
            this.nudStartingAmount.Location = new System.Drawing.Point(131, 22);
            this.nudStartingAmount.Maximum = new decimal(new int[] {
            1000000,
            0,
            0,
            0});
            this.nudStartingAmount.Name = "nudStartingAmount";
            this.nudStartingAmount.Size = new System.Drawing.Size(79, 23);
            this.nudStartingAmount.TabIndex = 21;
            this.nudStartingAmount.Value = new decimal(new int[] {
            1500,
            0,
            0,
            0});
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.label12.Location = new System.Drawing.Point(12, 24);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(113, 17);
            this.label12.TabIndex = 20;
            this.label12.Text = "Starting Amount:";
            // 
            // nudBlind
            // 
            this.nudBlind.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.nudBlind.Increment = new decimal(new int[] {
            100,
            0,
            0,
            0});
            this.nudBlind.Location = new System.Drawing.Point(285, 102);
            this.nudBlind.Maximum = new decimal(new int[] {
            1000,
            0,
            0,
            0});
            this.nudBlind.Minimum = new decimal(new int[] {
            5,
            0,
            0,
            0});
            this.nudBlind.Name = "nudBlind";
            this.nudBlind.Size = new System.Drawing.Size(52, 23);
            this.nudBlind.TabIndex = 29;
            this.nudBlind.Value = new decimal(new int[] {
            10,
            0,
            0,
            0});
            // 
            // lblBlind
            // 
            this.lblBlind.AutoSize = true;
            this.lblBlind.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.lblBlind.Location = new System.Drawing.Point(343, 104);
            this.lblBlind.Name = "lblBlind";
            this.lblBlind.Size = new System.Drawing.Size(73, 17);
            this.lblBlind.TabIndex = 28;
            this.lblBlind.Text = "(Big Blind)";
            // 
            // CreateTableTabControl
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.nudBlind);
            this.Controls.Add(this.lblBlind);
            this.Controls.Add(this.grpTraining);
            this.Controls.Add(this.grpTimes);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.lstBlinds);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.lstBetLimit);
            this.Controls.Add(this.nudNbPlayersMax);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.nudNbPlayersMin);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.txtTableName);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.lstVariant);
            this.Name = "CreateTableTabControl";
            this.Size = new System.Drawing.Size(468, 274);
            ((System.ComponentModel.ISupportInitialize)(this.nudNbPlayersMin)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudNbPlayersMax)).EndInit();
            this.grpTimes.ResumeLayout(false);
            this.grpTimes.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.nudWTAPotWon)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudWTABoardDealed)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudWTAPlayerAction)).EndInit();
            this.grpTraining.ResumeLayout(false);
            this.grpTraining.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.nudStartingAmount)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudBlind)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ComboBox lstVariant;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox txtTableName;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.NumericUpDown nudNbPlayersMin;
        private System.Windows.Forms.NumericUpDown nudNbPlayersMax;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.ComboBox lstBetLimit;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.ComboBox lstBlinds;
        private System.Windows.Forms.GroupBox grpTimes;
        private System.Windows.Forms.NumericUpDown nudWTAPlayerAction;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.NumericUpDown nudWTABoardDealed;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.NumericUpDown nudWTAPotWon;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.GroupBox grpTraining;
        private System.Windows.Forms.NumericUpDown nudStartingAmount;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.NumericUpDown nudBlind;
        private System.Windows.Forms.Label lblBlind;
    }
}
