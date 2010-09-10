namespace BluffinPokerClient
{
    partial class WelcomeForm
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
            this.mainVSplit = new System.Windows.Forms.SplitContainer();
            this.nudServerPort = new System.Windows.Forms.NumericUpDown();
            this.lblServerPort = new System.Windows.Forms.Label();
            this.clstServerName = new System.Windows.Forms.ComboBox();
            this.lblServerAddress = new System.Windows.Forms.Label();
            this.mainHSplit = new System.Windows.Forms.SplitContainer();
            this.lblTitNewPlayer = new System.Windows.Forms.Label();
            this.rightVSplit = new System.Windows.Forms.SplitContainer();
            this.lblTitExistPlayer = new System.Windows.Forms.Label();
            this.txtPlayerName = new System.Windows.Forms.TextBox();
            this.lblPlayerName = new System.Windows.Forms.Label();
            this.btnStartTraining = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.mainVSplit.Panel1.SuspendLayout();
            this.mainVSplit.Panel2.SuspendLayout();
            this.mainVSplit.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.nudServerPort)).BeginInit();
            this.mainHSplit.Panel1.SuspendLayout();
            this.mainHSplit.Panel2.SuspendLayout();
            this.mainHSplit.SuspendLayout();
            this.rightVSplit.Panel1.SuspendLayout();
            this.rightVSplit.Panel2.SuspendLayout();
            this.rightVSplit.SuspendLayout();
            this.SuspendLayout();
            // 
            // mainVSplit
            // 
            this.mainVSplit.Dock = System.Windows.Forms.DockStyle.Fill;
            this.mainVSplit.FixedPanel = System.Windows.Forms.FixedPanel.Panel1;
            this.mainVSplit.IsSplitterFixed = true;
            this.mainVSplit.Location = new System.Drawing.Point(0, 0);
            this.mainVSplit.Name = "mainVSplit";
            this.mainVSplit.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // mainVSplit.Panel1
            // 
            this.mainVSplit.Panel1.BackColor = System.Drawing.SystemColors.Control;
            this.mainVSplit.Panel1.Controls.Add(this.nudServerPort);
            this.mainVSplit.Panel1.Controls.Add(this.lblServerPort);
            this.mainVSplit.Panel1.Controls.Add(this.clstServerName);
            this.mainVSplit.Panel1.Controls.Add(this.lblServerAddress);
            // 
            // mainVSplit.Panel2
            // 
            this.mainVSplit.Panel2.Controls.Add(this.mainHSplit);
            this.mainVSplit.Size = new System.Drawing.Size(624, 446);
            this.mainVSplit.SplitterDistance = 33;
            this.mainVSplit.SplitterWidth = 1;
            this.mainVSplit.TabIndex = 0;
            // 
            // nudServerPort
            // 
            this.nudServerPort.Location = new System.Drawing.Point(528, 7);
            this.nudServerPort.Maximum = new decimal(new int[] {
            65535,
            0,
            0,
            0});
            this.nudServerPort.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.nudServerPort.Name = "nudServerPort";
            this.nudServerPort.Size = new System.Drawing.Size(84, 20);
            this.nudServerPort.TabIndex = 15;
            this.nudServerPort.Value = new decimal(new int[] {
            4242,
            0,
            0,
            0});
            // 
            // lblServerPort
            // 
            this.lblServerPort.AutoSize = true;
            this.lblServerPort.Location = new System.Drawing.Point(459, 9);
            this.lblServerPort.Name = "lblServerPort";
            this.lblServerPort.Size = new System.Drawing.Size(63, 13);
            this.lblServerPort.TabIndex = 14;
            this.lblServerPort.Text = "Server Port:";
            // 
            // clstServerName
            // 
            this.clstServerName.FormattingEnabled = true;
            this.clstServerName.Items.AddRange(new object[] {
            "127.0.0.1",
            "SRV-PRJ-05.dmi.usherb.ca"});
            this.clstServerName.Location = new System.Drawing.Point(100, 6);
            this.clstServerName.Name = "clstServerName";
            this.clstServerName.Size = new System.Drawing.Size(353, 21);
            this.clstServerName.TabIndex = 13;
            this.clstServerName.Text = "127.0.0.1";
            // 
            // lblServerAddress
            // 
            this.lblServerAddress.AutoSize = true;
            this.lblServerAddress.Location = new System.Drawing.Point(12, 9);
            this.lblServerAddress.Name = "lblServerAddress";
            this.lblServerAddress.Size = new System.Drawing.Size(82, 13);
            this.lblServerAddress.TabIndex = 5;
            this.lblServerAddress.Text = "Server Address:";
            // 
            // mainHSplit
            // 
            this.mainHSplit.Dock = System.Windows.Forms.DockStyle.Fill;
            this.mainHSplit.FixedPanel = System.Windows.Forms.FixedPanel.Panel2;
            this.mainHSplit.IsSplitterFixed = true;
            this.mainHSplit.Location = new System.Drawing.Point(0, 0);
            this.mainHSplit.Name = "mainHSplit";
            // 
            // mainHSplit.Panel1
            // 
            this.mainHSplit.Panel1.BackColor = System.Drawing.SystemColors.Control;
            this.mainHSplit.Panel1.Controls.Add(this.lblTitNewPlayer);
            // 
            // mainHSplit.Panel2
            // 
            this.mainHSplit.Panel2.Controls.Add(this.rightVSplit);
            this.mainHSplit.Size = new System.Drawing.Size(624, 412);
            this.mainHSplit.SplitterDistance = 357;
            this.mainHSplit.SplitterWidth = 1;
            this.mainHSplit.TabIndex = 1;
            // 
            // lblTitNewPlayer
            // 
            this.lblTitNewPlayer.Dock = System.Windows.Forms.DockStyle.Top;
            this.lblTitNewPlayer.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblTitNewPlayer.Location = new System.Drawing.Point(0, 0);
            this.lblTitNewPlayer.Name = "lblTitNewPlayer";
            this.lblTitNewPlayer.Size = new System.Drawing.Size(357, 38);
            this.lblTitNewPlayer.TabIndex = 0;
            this.lblTitNewPlayer.Text = "New Player";
            this.lblTitNewPlayer.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // rightVSplit
            // 
            this.rightVSplit.Dock = System.Windows.Forms.DockStyle.Fill;
            this.rightVSplit.FixedPanel = System.Windows.Forms.FixedPanel.Panel2;
            this.rightVSplit.IsSplitterFixed = true;
            this.rightVSplit.Location = new System.Drawing.Point(0, 0);
            this.rightVSplit.Name = "rightVSplit";
            this.rightVSplit.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // rightVSplit.Panel1
            // 
            this.rightVSplit.Panel1.BackColor = System.Drawing.SystemColors.Control;
            this.rightVSplit.Panel1.Controls.Add(this.lblTitExistPlayer);
            // 
            // rightVSplit.Panel2
            // 
            this.rightVSplit.Panel2.BackColor = System.Drawing.SystemColors.Control;
            this.rightVSplit.Panel2.Controls.Add(this.txtPlayerName);
            this.rightVSplit.Panel2.Controls.Add(this.lblPlayerName);
            this.rightVSplit.Panel2.Controls.Add(this.btnStartTraining);
            this.rightVSplit.Panel2.Controls.Add(this.label1);
            this.rightVSplit.Size = new System.Drawing.Size(266, 412);
            this.rightVSplit.SplitterDistance = 254;
            this.rightVSplit.SplitterWidth = 1;
            this.rightVSplit.TabIndex = 0;
            // 
            // lblTitExistPlayer
            // 
            this.lblTitExistPlayer.Dock = System.Windows.Forms.DockStyle.Top;
            this.lblTitExistPlayer.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblTitExistPlayer.Location = new System.Drawing.Point(0, 0);
            this.lblTitExistPlayer.Name = "lblTitExistPlayer";
            this.lblTitExistPlayer.Size = new System.Drawing.Size(266, 38);
            this.lblTitExistPlayer.TabIndex = 1;
            this.lblTitExistPlayer.Text = "Existing Player";
            this.lblTitExistPlayer.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // txtPlayerName
            // 
            this.txtPlayerName.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtPlayerName.Location = new System.Drawing.Point(32, 82);
            this.txtPlayerName.Name = "txtPlayerName";
            this.txtPlayerName.Size = new System.Drawing.Size(201, 22);
            this.txtPlayerName.TabIndex = 4;
            this.txtPlayerName.Text = "Player";
            // 
            // lblPlayerName
            // 
            this.lblPlayerName.AutoSize = true;
            this.lblPlayerName.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPlayerName.Location = new System.Drawing.Point(30, 51);
            this.lblPlayerName.Name = "lblPlayerName";
            this.lblPlayerName.Size = new System.Drawing.Size(90, 16);
            this.lblPlayerName.TabIndex = 3;
            this.lblPlayerName.Text = "Player Name:";
            // 
            // btnStartTraining
            // 
            this.btnStartTraining.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnStartTraining.Location = new System.Drawing.Point(33, 121);
            this.btnStartTraining.Margin = new System.Windows.Forms.Padding(0);
            this.btnStartTraining.Name = "btnStartTraining";
            this.btnStartTraining.Size = new System.Drawing.Size(200, 33);
            this.btnStartTraining.TabIndex = 3;
            this.btnStartTraining.Text = "Start Training";
            this.btnStartTraining.UseVisualStyleBackColor = true;
            this.btnStartTraining.Click += new System.EventHandler(this.btnStartTraining_Click);
            // 
            // label1
            // 
            this.label1.Dock = System.Windows.Forms.DockStyle.Top;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(0, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(266, 38);
            this.label1.TabIndex = 2;
            this.label1.Text = "Training";
            this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // WelcomeForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ControlDark;
            this.ClientSize = new System.Drawing.Size(624, 446);
            this.Controls.Add(this.mainVSplit);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.SizableToolWindow;
            this.MinimumSize = new System.Drawing.Size(640, 480);
            this.Name = "WelcomeForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Bluffin Muffin Client";
            this.mainVSplit.Panel1.ResumeLayout(false);
            this.mainVSplit.Panel1.PerformLayout();
            this.mainVSplit.Panel2.ResumeLayout(false);
            this.mainVSplit.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.nudServerPort)).EndInit();
            this.mainHSplit.Panel1.ResumeLayout(false);
            this.mainHSplit.Panel2.ResumeLayout(false);
            this.mainHSplit.ResumeLayout(false);
            this.rightVSplit.Panel1.ResumeLayout(false);
            this.rightVSplit.Panel2.ResumeLayout(false);
            this.rightVSplit.Panel2.PerformLayout();
            this.rightVSplit.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.SplitContainer mainVSplit;
        private System.Windows.Forms.SplitContainer mainHSplit;
        private System.Windows.Forms.Label lblTitNewPlayer;
        private System.Windows.Forms.SplitContainer rightVSplit;
        private System.Windows.Forms.Label lblTitExistPlayer;
        private System.Windows.Forms.Button btnStartTraining;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label lblPlayerName;
        private System.Windows.Forms.TextBox txtPlayerName;
        private System.Windows.Forms.Label lblServerAddress;
        private System.Windows.Forms.ComboBox clstServerName;
        private System.Windows.Forms.Label lblServerPort;
        private System.Windows.Forms.NumericUpDown nudServerPort;

    }
}