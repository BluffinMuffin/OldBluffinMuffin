namespace BluffinPokerGUI.Lobby
{
    partial class AddTableControl
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
            this.grpWTA = new System.Windows.Forms.GroupBox();
            this.nudWTAPotWon = new System.Windows.Forms.NumericUpDown();
            this.lblWTAPotWon = new System.Windows.Forms.Label();
            this.nudWTABoardDealed = new System.Windows.Forms.NumericUpDown();
            this.lblWTABoardDealed = new System.Windows.Forms.Label();
            this.nudWTAPlayerAction = new System.Windows.Forms.NumericUpDown();
            this.lblWTAPlayerAction = new System.Windows.Forms.Label();
            this.nudNbPlayers = new System.Windows.Forms.NumericUpDown();
            this.lblNbPlayers = new System.Windows.Forms.Label();
            this.nudBigBlindAmnt = new System.Windows.Forms.NumericUpDown();
            this.lblBigBlindAmnt = new System.Windows.Forms.Label();
            this.clstGameLimit = new System.Windows.Forms.ComboBox();
            this.lblGameLimit = new System.Windows.Forms.Label();
            this.txtTableName = new System.Windows.Forms.TextBox();
            this.lblTableName = new System.Windows.Forms.Label();
            this.nudNbPlayersMin = new System.Windows.Forms.NumericUpDown();
            this.grpWTA.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.nudWTAPotWon)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudWTABoardDealed)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudWTAPlayerAction)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudNbPlayers)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudBigBlindAmnt)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudNbPlayersMin)).BeginInit();
            this.SuspendLayout();
            // 
            // grpWTA
            // 
            this.grpWTA.Controls.Add(this.nudWTAPotWon);
            this.grpWTA.Controls.Add(this.lblWTAPotWon);
            this.grpWTA.Controls.Add(this.nudWTABoardDealed);
            this.grpWTA.Controls.Add(this.lblWTABoardDealed);
            this.grpWTA.Controls.Add(this.nudWTAPlayerAction);
            this.grpWTA.Controls.Add(this.lblWTAPlayerAction);
            this.grpWTA.Location = new System.Drawing.Point(0, 82);
            this.grpWTA.Name = "grpWTA";
            this.grpWTA.Size = new System.Drawing.Size(291, 70);
            this.grpWTA.TabIndex = 17;
            this.grpWTA.TabStop = false;
            this.grpWTA.Text = "Waiting time after ... (ms)";
            // 
            // nudWTAPotWon
            // 
            this.nudWTAPotWon.Increment = new decimal(new int[] {
            500,
            0,
            0,
            0});
            this.nudWTAPotWon.Location = new System.Drawing.Point(198, 38);
            this.nudWTAPotWon.Maximum = new decimal(new int[] {
            60000,
            0,
            0,
            0});
            this.nudWTAPotWon.Name = "nudWTAPotWon";
            this.nudWTAPotWon.Size = new System.Drawing.Size(84, 20);
            this.nudWTAPotWon.TabIndex = 7;
            this.nudWTAPotWon.Value = new decimal(new int[] {
            2500,
            0,
            0,
            0});
            // 
            // lblWTAPotWon
            // 
            this.lblWTAPotWon.AutoSize = true;
            this.lblWTAPotWon.Location = new System.Drawing.Point(195, 22);
            this.lblWTAPotWon.Name = "lblWTAPotWon";
            this.lblWTAPotWon.Size = new System.Drawing.Size(52, 13);
            this.lblWTAPotWon.TabIndex = 10;
            this.lblWTAPotWon.Text = "Pot Won:";
            // 
            // nudWTABoardDealed
            // 
            this.nudWTABoardDealed.Increment = new decimal(new int[] {
            500,
            0,
            0,
            0});
            this.nudWTABoardDealed.Location = new System.Drawing.Point(102, 38);
            this.nudWTABoardDealed.Maximum = new decimal(new int[] {
            60000,
            0,
            0,
            0});
            this.nudWTABoardDealed.Name = "nudWTABoardDealed";
            this.nudWTABoardDealed.Size = new System.Drawing.Size(84, 20);
            this.nudWTABoardDealed.TabIndex = 6;
            this.nudWTABoardDealed.Value = new decimal(new int[] {
            500,
            0,
            0,
            0});
            // 
            // lblWTABoardDealed
            // 
            this.lblWTABoardDealed.AutoSize = true;
            this.lblWTABoardDealed.Location = new System.Drawing.Point(99, 22);
            this.lblWTABoardDealed.Name = "lblWTABoardDealed";
            this.lblWTABoardDealed.Size = new System.Drawing.Size(75, 13);
            this.lblWTABoardDealed.TabIndex = 8;
            this.lblWTABoardDealed.Text = "Board Dealed:";
            // 
            // nudWTAPlayerAction
            // 
            this.nudWTAPlayerAction.Increment = new decimal(new int[] {
            500,
            0,
            0,
            0});
            this.nudWTAPlayerAction.Location = new System.Drawing.Point(9, 38);
            this.nudWTAPlayerAction.Maximum = new decimal(new int[] {
            60000,
            0,
            0,
            0});
            this.nudWTAPlayerAction.Name = "nudWTAPlayerAction";
            this.nudWTAPlayerAction.Size = new System.Drawing.Size(84, 20);
            this.nudWTAPlayerAction.TabIndex = 5;
            this.nudWTAPlayerAction.Value = new decimal(new int[] {
            500,
            0,
            0,
            0});
            // 
            // lblWTAPlayerAction
            // 
            this.lblWTAPlayerAction.AutoSize = true;
            this.lblWTAPlayerAction.Location = new System.Drawing.Point(6, 22);
            this.lblWTAPlayerAction.Name = "lblWTAPlayerAction";
            this.lblWTAPlayerAction.Size = new System.Drawing.Size(72, 13);
            this.lblWTAPlayerAction.TabIndex = 6;
            this.lblWTAPlayerAction.Text = "Player Action:";
            // 
            // nudNbPlayers
            // 
            this.nudNbPlayers.Location = new System.Drawing.Point(207, 16);
            this.nudNbPlayers.Maximum = new decimal(new int[] {
            10,
            0,
            0,
            0});
            this.nudNbPlayers.Minimum = new decimal(new int[] {
            2,
            0,
            0,
            0});
            this.nudNbPlayers.Name = "nudNbPlayers";
            this.nudNbPlayers.Size = new System.Drawing.Size(40, 20);
            this.nudNbPlayers.TabIndex = 12;
            this.nudNbPlayers.Value = new decimal(new int[] {
            10,
            0,
            0,
            0});
            this.nudNbPlayers.ValueChanged += new System.EventHandler(this.nudNbPlayers_ValueChanged);
            // 
            // lblNbPlayers
            // 
            this.lblNbPlayers.AutoSize = true;
            this.lblNbPlayers.Location = new System.Drawing.Point(204, 0);
            this.lblNbPlayers.Name = "lblNbPlayers";
            this.lblNbPlayers.Size = new System.Drawing.Size(96, 13);
            this.lblNbPlayers.TabIndex = 16;
            this.lblNbPlayers.Text = "Players (Max - Min)";
            // 
            // nudBigBlindAmnt
            // 
            this.nudBigBlindAmnt.Increment = new decimal(new int[] {
            5,
            0,
            0,
            0});
            this.nudBigBlindAmnt.Location = new System.Drawing.Point(207, 56);
            this.nudBigBlindAmnt.Maximum = new decimal(new int[] {
            1000,
            0,
            0,
            0});
            this.nudBigBlindAmnt.Minimum = new decimal(new int[] {
            10,
            0,
            0,
            0});
            this.nudBigBlindAmnt.Name = "nudBigBlindAmnt";
            this.nudBigBlindAmnt.Size = new System.Drawing.Size(84, 20);
            this.nudBigBlindAmnt.TabIndex = 14;
            this.nudBigBlindAmnt.Value = new decimal(new int[] {
            10,
            0,
            0,
            0});
            // 
            // lblBigBlindAmnt
            // 
            this.lblBigBlindAmnt.AutoSize = true;
            this.lblBigBlindAmnt.Location = new System.Drawing.Point(204, 40);
            this.lblBigBlindAmnt.Name = "lblBigBlindAmnt";
            this.lblBigBlindAmnt.Size = new System.Drawing.Size(90, 13);
            this.lblBigBlindAmnt.TabIndex = 15;
            this.lblBigBlindAmnt.Text = "Big Blind Amount:";
            // 
            // clstGameLimit
            // 
            this.clstGameLimit.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.clstGameLimit.FormattingEnabled = true;
            this.clstGameLimit.Location = new System.Drawing.Point(0, 55);
            this.clstGameLimit.Name = "clstGameLimit";
            this.clstGameLimit.Size = new System.Drawing.Size(201, 21);
            this.clstGameLimit.TabIndex = 13;
            // 
            // lblGameLimit
            // 
            this.lblGameLimit.AutoSize = true;
            this.lblGameLimit.Location = new System.Drawing.Point(-3, 39);
            this.lblGameLimit.Name = "lblGameLimit";
            this.lblGameLimit.Size = new System.Drawing.Size(62, 13);
            this.lblGameLimit.TabIndex = 10;
            this.lblGameLimit.Text = "Game Limit:";
            // 
            // txtTableName
            // 
            this.txtTableName.Location = new System.Drawing.Point(0, 16);
            this.txtTableName.Name = "txtTableName";
            this.txtTableName.Size = new System.Drawing.Size(201, 20);
            this.txtTableName.TabIndex = 11;
            // 
            // lblTableName
            // 
            this.lblTableName.AutoSize = true;
            this.lblTableName.Location = new System.Drawing.Point(-3, 0);
            this.lblTableName.Name = "lblTableName";
            this.lblTableName.Size = new System.Drawing.Size(68, 13);
            this.lblTableName.TabIndex = 9;
            this.lblTableName.Text = "Table Name:";
            // 
            // nudNbPlayersMin
            // 
            this.nudNbPlayersMin.Location = new System.Drawing.Point(254, 16);
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
            this.nudNbPlayersMin.Size = new System.Drawing.Size(40, 20);
            this.nudNbPlayersMin.TabIndex = 18;
            this.nudNbPlayersMin.Value = new decimal(new int[] {
            2,
            0,
            0,
            0});
            // 
            // AddTableControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.nudNbPlayersMin);
            this.Controls.Add(this.grpWTA);
            this.Controls.Add(this.nudNbPlayers);
            this.Controls.Add(this.lblNbPlayers);
            this.Controls.Add(this.nudBigBlindAmnt);
            this.Controls.Add(this.lblBigBlindAmnt);
            this.Controls.Add(this.clstGameLimit);
            this.Controls.Add(this.lblGameLimit);
            this.Controls.Add(this.txtTableName);
            this.Controls.Add(this.lblTableName);
            this.Name = "AddTableControl";
            this.Size = new System.Drawing.Size(299, 157);
            this.grpWTA.ResumeLayout(false);
            this.grpWTA.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.nudWTAPotWon)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudWTABoardDealed)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudWTAPlayerAction)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudNbPlayers)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudBigBlindAmnt)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudNbPlayersMin)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.GroupBox grpWTA;
        private System.Windows.Forms.NumericUpDown nudWTAPotWon;
        private System.Windows.Forms.Label lblWTAPotWon;
        private System.Windows.Forms.NumericUpDown nudWTABoardDealed;
        private System.Windows.Forms.Label lblWTABoardDealed;
        private System.Windows.Forms.NumericUpDown nudWTAPlayerAction;
        private System.Windows.Forms.Label lblWTAPlayerAction;
        private System.Windows.Forms.NumericUpDown nudNbPlayers;
        private System.Windows.Forms.Label lblNbPlayers;
        private System.Windows.Forms.NumericUpDown nudBigBlindAmnt;
        private System.Windows.Forms.Label lblBigBlindAmnt;
        private System.Windows.Forms.ComboBox clstGameLimit;
        private System.Windows.Forms.Label lblGameLimit;
        private System.Windows.Forms.TextBox txtTableName;
        private System.Windows.Forms.Label lblTableName;
        private System.Windows.Forms.NumericUpDown nudNbPlayersMin;
    }
}
